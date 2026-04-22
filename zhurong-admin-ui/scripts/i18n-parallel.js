/**
 * 国际化改造 - 并行处理脚本
 * 
 * 功能说明：
 * 1. 自动扫描指定目录下的所有 .vue 文件
 * 2. 识别文件中的硬编码中文文本
 * 3. 按照既定的 i18n 规范进行批量替换
 * 4. 支持多进程/并发执行（通过 worker_threads 或 Promise 并行）
 * 5. 提供详细的处理报告和错误日志
 * 
 * 使用方式：
 * node scripts/i18n-parallel.js [路径] [--dry-run] [--report] [--fix]
 * 
 * @author CodeBuddy AI
 * @version 1.0.0
 */

const fs = require('fs');
const path = require('path');
const { Worker, isMainThread, parentPort } = require('worker_threads');
const os = require('os');

// ==================== 配置区 ====================

const CONFIG = {
  // 目标扫描目录
  targetDir: process.argv[2] || 'src/views',
  
  // 项目根目录
  rootDir: path.resolve(__dirname, '..'),
  
  // 并发数（根据 CPU 核心数动态设置）
  concurrency: os.cpus().length || 4,
  
  // 文件扩展名
  extensions: ['.vue'],
  
  // 是否只读模式（不实际修改文件）
  dryRun: process.argv.includes('--dry-run'),
  
  // 是否生成报告
  report: process.argv.includes('--report'),
  
  // 是否自动修复
  fix: process.argv.includes('--fix'),
  
  // 输出报告目录
  reportDir: path.resolve(__dirname, '..', 'reports', 'i18n'),
};

// ==================== 国际化映射规则 ====================

/**
 * 中文文本到 i18n key 的自动映射规则
 * 
 * 规则优先级从高到低：
 * 1. 精确匹配 - 完全一致的文本直接映射
 * 2. 模式匹配 - 常见文本模式正则匹配
 * 3. 上下文推断 - 根据所在标签/属性推断合适的命名空间
 */
const I18N_MAPPING_RULES = {
  
  // ========== 表单标签 (form.*) ==========
  'label': {
    pattern: /label="([^"]*[\u4e00-\u9fa5][^"]*"/g,
    namespace: 'form',
    examples: {
      '用户名称': 'userName',
      '用户昵称': 'nickName',
      '手机号码': 'phonenumber',
      '邮箱': 'email',
      '密码': 'password',
      '状态': 'status',
      '备注': 'remark',
      '岗位名称': 'postName',
      '角色名称': 'roleName',
      '部门名称': 'deptName',
      '菜单名称': 'menuName',
      '字典名称': 'dictName',
      '排序': 'orderNum',
      '显示顺序': 'showOrder',
      '权限字符': 'roleKey',
      '上级菜单': 'parentMenu',
      '菜单类型': 'menuType',
      '图标': 'icon',
      '路由地址': 'path',
      '组件路径': 'component',
      '权限标识': 'perms',
      '创建时间': 'createTime',
      '更新时间': 'updateTime'
    }
  },

  // ========== 输入框提示 (placeholder.*) ==========
  'placeholder': {
    pattern: /placeholder="([^"]*[\u4e00-\u9fa5][^"]*"/g,
    namespace: 'placeholder',
    examples: {
      '请输入': 'pleaseInput',
      '请选择': 'pleaseSelect',
      '请上传': 'upload',
      '账号': 'username',
      '密码': 'password',
      '验证码': 'captcha',
      '用户名': 'userName',
      '角色名': 'roleName',
      '岗位名': 'postName',
      '部门名': 'deptName',
      '字典类型': 'dictType',
      '菜单名': 'menuName',
      '开始日期': 'startDate',
      '结束日期': 'endDate',
      '开始时间': 'startTime',
      '结束时间': 'endTime',
      '状态': 'selectStatus',
      '性别': 'selectGender',
      '岗位': 'selectPost',
      '角色': 'selectRole',
      '部门': 'selectDept',
      '编码': 'postCode'
    }
  },

  // ========== 按钮文本 (button.*) ==========
  'button': {
    pattern: />[^<]*[\u4e00-\u9fa5]+[^<]*</g,
    namespace: 'button',
    examples: {
      '查询': 'search',
      '重置': 'reset',
      '新增': 'add',
      '添加': 'add',
      '编辑': 'edit',
      '修改': 'edit',
      '删除': 'delete',
      '导入': 'import',
      '导出': 'export',
      '下载': 'download',
      '上传': 'upload',
      '提交': 'submit',
      '确定': 'submit',
      '取消': 'cancel',
      '关闭': 'close',
      '保存': 'save',
      '更多': 'more',
      '刷新': 'refresh',
      '展开': 'expand',
      '收起': 'collapse',
      '返回': 'back',
      '查看': 'view',
      '操作': 'operate',
      '确认': 'confirm',
      '选择': 'select',
      '清空': 'clear',
      '生成': 'generate',
      '预览': 'preview',
      '复制': 'copy',
      '启用': 'enable',
      '禁用': 'disable',
      '启动': 'start',
      '停止': 'stop',
      '执行': 'run',
      '详情': 'detail',
      '日志': 'log',
      '授权': 'auth',
      '重置密码': 'resetPwd',
      '分配角色': 'authRole',
      '分配用户': 'authUser'
    }
  },

  // ========== 表格列标题 (table.*) ==========
  'tableColumn': {
    pattern: /label="([^"]*[\u4e00-\u9fa5][^"]*"/g,
    namespace: 'table',
    examples: {
      '编号': 'Id',
      '名称': 'Name',
      '编号': 'userId',
      '用户名称': 'userName',
      '用户昵称': 'nickName',
      '部门': 'deptName',
      '手机号码': 'phonenumber',
      '状态': 'status',
      '类型': 'Type',
      '时间': 'Time',
      '操作': 'operate',
      '创建时间': 'createTime',
      '更新时间': 'updateTime',
      '排序': 'orderNum',
      '备注': 'remark',
      '值': 'Value',
      '键': 'Key'
    }
  },

  // ========== 提示消息/对话框 (message.* / confirm.*) ==========
  'message': {
    pattern: /(message|msgSuccess|msgError|modal\.confirm)\(['"`])([^'"]*[\u4e00-\u9fa5][^'"]*)/g,
    namespace: 'message',
    examples: {
      '成功': 'success',
      '失败': 'failed',
      '修改成功': 'editSuccess',
      '新增成功': 'addSuccess',
      '删除成功': 'deleteSuccess',
      '保存成功': 'saveSuccess',
      '导入成功': 'importSuccess',
      '导出成功': 'exportSuccess',
      '上传成功': 'uploadSuccess',
      '下载成功': 'downloadSuccess',
      '操作成功': 'operationSuccess',
      '是否确认删除': 'deleteConfirm',
      '是否确认': 'confirm'
    }
  }
};

// ========== 需要忽略的常见模式 ==========
const IGNORE_PATTERNS = [
  /console\.(log|warn|error)/,           // console 日志
  /\/\/[\s]*TODO/,                     // TODO 注释
  /\/\*[\s\S]*?\*\//,                // 多行注释
  /<style[\s]*>[\s\S]*<\/style>/,     // style 标签内容
  /<script[\s]*>[\s\S]*<\/script>/,   // script 标签内容（单独处理）
];

// ==================== 工具函数 ====================

/**
 * 计算字符串中中文字符的数量
 */
function countChineseChars(str) {
  const matches = str.match(/[\u4e00-\u9fa5]/g);
  return matches ? matches.length : 0;
}

/**
 * 判断文本是否包含中文
 */
function containsChinese(text) {
  return /[\u4e00-\u9fa5]/.test(text);
}

/**
 * 将 camelCase 转为 kebab-case
 */
function toKebabCase(str) {
  return str.replace(/([a-z0-9])([A-Z])/g, '$1-$2').toLowerCase();
}

/**
 * 根据中文文本和上下文推断 i18n key
 */
function inferI18nKey(text, context = {}) {
  const trimmedText = text.trim();
  
  // 1. 精确匹配已有示例
  for (const [, rule] of Object.entries(I18N_MAPPING_RULES)) {
    if (rule.examples && rule.examples[trimmedText]) {
      return `${rule.namespace}.${rule.examples[trimmedText]}`;
    }
  }
  
  // 2. 根据上下文推断命名空间
  let namespace = 'common';
  if (context.isLabel) namespace = 'form';
  if (context.isPlaceholder) namespace = 'placeholder';
  if (context.isButtonContent) namespace = 'button';
  if (context.isTableHeader) namespace = 'table';
  if (context.isMessage) namespace = 'message';
  
  // 3. 生成基于文本的 key（简化版）
  const baseKey = trimmedText
    .replace(/[\u4e00-\u9fa5]/g, '')  // 移除中文
    .replace(/[^a-zA-Z0-9]/g, '')   // 只保留字母数字
    .replace(/^[0-9]/, '_');       // 数字开头加下划线
  
  return namespace + '.' + (baseKey || 'custom');
}

/**
 * 解析 Vue 文件中的模板部分
 */
function parseTemplate(content) {
  const results = [];
  
  // 匹配 :label="..." 或 label="..."
  const labelPattern = /\s+(?:label|placeholder|:label|:placeholder)="([^"]*(?=[^"]*")/g;
  let match;
  
  while ((match = labelPattern.exec(content)) !== null) {
    const text = match[2];
    if (containsChinese(text)) {
      results.push({
        type: match[1]?.replace(':', '') || 'label',
        rawText: text,
        fullMatch: match[0],
        index: match.index,
        end: match.index + match[0].length,
        suggestedKey: inferI18nKey(text, { isLabel: true })
      });
    }
  }
  
  // 匹配 >按钮文本< 或 >文本<
  const buttonPattern = />([^<]*[\u4e00-\u9fa5]+[^<]*)</g;
  while ((match = buttonPattern.exec(content)) !== null) {
    const text = match[1].trim();
    // 排除已处理的 label/placeholder 和太长的文本
    if (containsChinese(text) && !results.find(r => r.fullMatch === match[0])) {
      results.push({
        type: 'button',
        rawText: text,
        fullMatch: match[0],
        index: match.index,
        end: match.index + match[0].length,
        suggestedKey: inferI18nKey(text, { isButtonContent: true })
      });
    }
  }
  
  // 匹配 '硬编码字符串'
  const stringPattern = /['"`]([^'"`]*[\u4e00-\u9fa5]+[^'"`]*)['"`]/g;
  while ((match = stringPattern.exec(content)) !== null) {
    const text = match[1];
    // 简单过滤掉明显不需要国际化的
    if (containsChinese(text) && text.length < 50) {
      results.push({
        type: 'string',
        rawText: text,
        fullMatch: match[0],
        index: match.index,
        end: match.index + match[0].length,
        suggestedKey: inferI18nKey(text, { isMessage: true })
      });
    }
  }
  
  return results;
}

/**
 * 解析 Vue 文件中的 script 部分
 */
function parseScript(content) {
  const results = [];
  
  // 匹配 this.$t('...') 或 $t('...')
  const existingT = content.match(/\$t\s*\(\s*['"`][^'"`]*['"`]\s*\)/g);
  if (existingT) {
    results.existingI18nCount = existingT.length;
  }
  
  // 匹配硬编码的中文字符串
  // 例如: "修改成功", '确认要删除吗？'
  const patterns = [
    // message: "...", msgSuccess("...")
    { pattern: /(?:modal\.)?(?:msg(?:Success|Error|Warning|Info))\((?:\s*['"`]|["'])([^'"]*[\u4e00-\u9fa5]+[^'"]*/g, type: 'message' },
    // confirm('...')
    { pattern: /(?:\$modal)?\.confirm\((?:\s*['"`]|["'])([^'"]*[\u4e00-\u9fa5]+[^'"]*/g, type: 'confirm' },
    // title: "..."
    { pattern: /this\.title\s*=\s*["'][^"']*[\u4e00-\u9fa5]+[^"']*["']/g, type: 'title' },
    // rules: {...}
    { pattern: /message:\s*["'][^"']+[\u4e00-\u9fa5]+[^"']+"/g, type: 'rules' },
  ];
  
  for (const { pattern, type } of patterns) {
    let match;
    while ((match = pattern.exec(content)) !== null) {
      const text = match[1];
      if (!results.find(r => r.fullMatch === match[0])) {
        results.push({
          type,
          rawText: text,
          fullMatch: match[0],
          index: match.index,
          end: match.index + match[0].length,
          suggestedKey: inferI18nKey(text, {})
        });
      }
    }
  }
  
  return results;
}

// ==================== 文件处理器 ====================

class I18nFileProcessor {
  constructor(filePath) {
    this.filePath = filePath;
    this.content = null;
    this.templateResults = [];
    this.scriptResults = [];
    this.stats = {
      totalChinese: 0,
      totalReplacements: 0,
      existingI18n: 0,
      errors: []
    };
  }

  /**
   * 读取并解析文件
   */
  async parse() {
    try {
      this.content = fs.readFileSync(this.filePath, 'utf8');
      
      // 分离 template 和 script 部分
      const templateMatch = this.content.match(/<template>[\s\S]*?<\/template>/);
      const scriptMatch = this.content.match(/<script>[\s\S]*?<\/script>/);
      
      if (templateMatch) {
        this.templateResults = parseTemplate(templateMatch[0]);
      }
      if (scriptMatch) {
        this.scriptResults = parseScript(scriptMatch[0]);
        
        // 统计已有的 i18n 使用
        const tMatches = scriptMatch[0].match(/\$t\s*\(/g);
        this.stats.existingI18n = tMatches ? tMatches.length : 0;
      }
      
      // 统计总中文字符数
      this.stats.totalChinese = countChineseChars(this.content);
      
      // 合并结果（去重）
      this.allResults = this._mergeResults(this.templateResults, this.scriptResults);
      this.stats.totalReplacements = this.allResults.length;
      
      return this;
    } catch (error) {
      this.stats.errors.push(`读取文件失败: ${error.message}`);
      return this;
    }
  }

  /**
   * 合并并去重结果
   */
  _mergeResults(templateResults, scriptResults) {
    const merged = [...templateResults];
    const seen = new Set(templateResults.map(r => r.fullMatch));
    
    for (const result of scriptResults) {
      if (!seen.has(result.fullMatch)) {
        merged.push(result);
        seen.add(result.fullMatch);
      }
    }
    
    return merged;
  }

  /**
   * 生成替换后的内容
   */
  generateFixedContent() {
    if (!this.content) return null;
    
    let fixedContent = this.content;
    const replacements = [];
    
    for (const result of this.allResults) {
      const replacement = this._generateReplacement(result);
      replacements.push({ ...result, replacement });
    }
    
    // 从后往前替换，避免位置偏移问题
    replacements.sort((a, b) => b.index - a.index);
    
    for (const repl of replacements) {
      if (repl.type === 'label') {
        fixedContent = fixedContent.replace(repl.fullMatch, `:${repl.fullMatch}`);
        fixedContent = fixedContent.replace(
          repl.rawText,
          `{{ \$t('${repl.suggestedKey}') }}`
        );
      } else if (repl.type === 'placeholder') {
        fixedContent = fixedContent.replace(repl.fullMatch, `:${repl.fullMatch}`);
        fixedContent = fixedContent.replace(
          repl.rawText,
          `:\$t('${repl.suggestedKey}')`
        );
      } else if (repl.type === 'button') {
        fixedContent = fixedContent.replace(
          repl.rawText,
          `{{ \$t('${repl.suggestedKey}') }}`
        );
      } else if (repl.type === 'string') {
        fixedContent = fixedContent.replace(
          `"${repl.rawText}"`,
          `this.\$t('${repl.suggestedKey}')`
        );
      } else if (repl.type === 'message' || repl.type === 'confirm') {
        fixedContent = fixedContent.replace(
          `"${repl.rawText}"`,
          `this.\$t('${repl.suggestedKey}')`
        );
      } else if (repl.type === 'title') {
        fixedContent = fixedContent.replace(
          `"${repl.rawText}"`,
          `this.\$t('${repl.suggestedKey}')`
        );
      } else if (repl.type === 'rules') {
        fixedContent = fixedContent.replace(
          `"${repl.rawText}"`,
          `this.\$t('${repl.suggestedKey}')`
        );
      }
    }
    
    return fixedContent;
  }

  /**
   * 生成单个替换项
   */
  _generateReplacement(result) {
    switch (result.type) {
      case 'label':
        return `:label="\${'$t('${result.suggestedKey}')"`;
      case 'placeholder':
        return `:placeholder="\${'$t('${result.suggestedKey}')"`;
      case 'button':
        return `{{ \$t('${result.suggestedKey}') }}`;
      case 'string':
        return `this.$t('${result.suggestedKey}')`;
      default:
        return `this.$t('${result.suggestedKey}')`;
    }
  }

  /**
   * 写入修复后的文件
   */
  async fix() {
    const fixedContent = this.generateFixedContent();
    if (fixedContent) {
      await fs.promises.writeFile(this.filePath, fixedContent, 'utf-8');
      return true;
    }
    return false;
  }

  /**
   * 生成报告
   */
  generateReport() {
    return {
      file: path.relative(CONFIG.rootDir, this.filePath),
      stats: this.stats,
      results: this.allResults.slice(0, 50), // 限制显示数量
    };
  }
}

// ==================== 并行处理管理器 ====================

class ParallelProcessor {
  constructor() {
    this.files = [];
    this.results = [];
    this.errors = [];
    this.startTime = Date.now();
  }

  /**
   * 扫描目录获取所有需要处理的 Vue 文件
   */
  async scanDirectory(dir) {
    const files = [];
    const walkDir = async (currentDir) => const entries = await fs.promises.readdir(currentDir, { withFileTypes: true });
    
    const entries = await walkDir(dir);
    
    for (const entry of entries) {
      const fullPath = path.join(entry.path, entry.name);
      
      if (entry.isDirectory()) {
        const subFiles = await this.scanDirectory(fullPath);
        files.push(...subFiles);
      } else if (CONFIG.extensions.includes(path.extname(entry.name))) {
        files.push(fullPath);
      }
    }
    
    return files;
  }

  /**
   * 过滤出包含中文的文件
   */
  filterChineseFiles(files) {
    const chineseFiles = [];
    
    for (const file of files) {
      try {
        const content = fs.readFileSync(file, 'utf8');
        if (containsChinese(content)) {
          chineseFiles.push(file);
        }
      } catch (error) {
        this.errors.push({ file, error: error.message });
      }
    }
    
    return chineseFiles;
  }

  /**
   * 并行处理所有文件
   */
  async processAll(files) {
    const chunkSize = Math.ceil(files.length / CONFIG.concurrency);
    const results = [];
    
    console.log(`📊 开始并行处理 ${files.length} 个文件`);
    console.log(`⚙️  并发数: ${CONFIG.concurrency}`);
    console.log(`📂 目标目录: ${CONFIG.targetDir}\n`);
    
    // 分批处理
    for (let i = 0; i < files.length; i += chunkSize) {
      const chunk = files.slice(i, i + chunkSize);
      const chunkResults = await Promise.allSettled(
        chunk.map(async (file) => {
          const processor = new I18nFileProcessor(file);
          return await processor.parse();
        })
      );
      
      results.push(...chunkResults);
      
      // 显示进度
      const processed = Math.min(i + chunkSize, files.length);
      console.log(`✅ 已处理: ${processed}/${files.length} (${Math.round(processed/files.length * 100)}%)`);
    }
    
    this.results = results.map((r, i) => ({
      file: files[i],
      processor: r.status === 'fulfilled' ? r.value : null,
      error: r.status === 'rejected' ? r.reason : null
    }));
    
    return this;
  }

  /**
   * 自动修复所有文件
   */
  async fixAll() {
    const processors = this.results
      .filter(r => r.processor)
      .map(r => r.processor);
    
    console.log(`\n🔧 开始修复 ${processors.length} 个文件...\n`);
    
    let successCount = 0;
    let failCount = 0;
    
    for (const processor of processors) {
      try {
        const success = await processor.fix();
        if (success) {
          successCount++;
        } else {
          failCount++;
        }
      } catch (error) {
        failCount++;
        this.errors.push({ file: processor.filePath, error: error.message });
      }
    }
    
    console.log(`\n✅ 修复完成: 成功 ${successCount}, 失败 ${failCount}`);
    return { successCount, failCount };
  }

  /**
   * 生成汇总报告
   */
  generateSummaryReport() {
    const validProcessors = this.results.filter(r => r.processor).map(r => r.processor);
    
    const summary = {
      meta: {
        startTime: this.startTime.toISOString(),
        endTime: new Date().toISOString(),
        duration: Date.now() - this.startTime,
        totalFilesScanned: this.results.length,
        totalChineseFiles: validProcessors.length,
        concurrency: CONFIG.concurrency,
        dryRun: CONFIG.dryRun
      },
      overview: {
        totalReplacements: validProcessors.reduce((sum, p) => sum + p.stats.totalReplacements, 0),
        existingI18n: validProcessors.reduce((sum, p) => sum + p.stats.existingI18n, 0),
        totalErrors: this.errors.length
      },
      details: validProcessors.map(p => p.generateReport()),
      errors: this.errors.slice(0, 100),
      recommendations: this._generateRecommendations(validProcessors)
    };
    
    return summary;
  }

  /**
   * 生成改进建议
   */
  _generateRecommendations(processors) {
    const recommendations = [];
    
    // 找出未使用任何 i18n 的文件
    const noI18nFiles = processors.filter(p => p.stats.existingI18n === 0);
    if (noI18nFiles.length > 0) {
      recommendations.push({
        priority: 'high',
        category: 'unprocessed',
        message: `${noI18nFiles.length} 个文件尚未进行国际化改造`,
        files: noI18nFiles.slice(0, 10).map(p => p.filePath)
      });
    }
    
    // 找出高优先级文件（大量硬编码中文）
    const highPriorityFiles = processors
      .filter(p => p.stats.totalReplacements > 20)
      .sort((a, b) => b.stats.totalReplacements - a.stats.totalReplacements)
      .slice(0, 10);
    if (highPriorityFiles.length > 0) {
      recommendations.push({
        priority: 'high',
        category: 'high-volume',
        message: `${highPriorityFiles.length} 个文件包含大量硬编码中文（>20处）`,
        files: highPriorityFiles.map(p => ({
          file: p.filePath,
          count: p.stats.totalReplacements
        }))
      });
    }
    
    // 找出有错误的文件
    if (this.errors.length > 0) {
      recommendations.push({
        priority: 'critical',
        category: 'errors',
        message: `${this.errors.length} 个文件处理出错`,
        files: this.errors.slice(0, 5).map(e => ({ file: e.file, error: e.error }))
      });
    }
    
    return recommendations;
  }

  /**
   * 将报告写入文件
   */
  async saveReport() {
    if (!fs.existsSync(CONFIG.reportDir)) {
      fs.mkdirSync(CONFIG.reportDir, { recursive: true });
    }
    
    const report = this.generateSummaryReport();
    const reportPath = path.join(CONFIG.reportDir, `i18n-report-${Date.now()}.json`);
    
    await fs.promises.writeFile(reportPath, JSON.stringify(report, null, 2), 'utf-8');
    
    // 同时生成可读的 Markdown 报告
    const mdReport = this._generateMarkdownReport(report);
    const mdPath = path.join(CONFIG.reportDir, `i18n-report-${Date.now()}.md`);
    await fs.promises.writeFile(mdPath, mdReport, 'utf-8');
    
    console.log(`\n📋 报告已保存:`);
    console.log(`   JSON: ${reportPath}`);
    console.log   MD:  ${mdPath}`);
    
    return { reportPath, mdPath };
  }

  /**
   * 生成 Markdown 格式的报告
   */
  _generateMarkdownReport(report) {
    const lines = [
      '# 国际化改造报告\n',
      `- **生成时间**: ${new Date().toLocaleString()}`,
      `- **处理耗时**: ${report.meta.duration}ms}`,
      `- **扫描文件**: ${report.meta.totalFilesScanned}`,
      `- **含中文文件**: ${report.meta.totalChineseFiles}`,
      `- **总替换点**: ${report.overview.totalReplacements}`,
      '- **已有i18n调用**: **` + report.overview.existingI18n + '**\n',
      '---',
      '\n## 📊 改造进度\n',
      '| 指标 | 数量 |',
      '|------|------|',
      `| 扫描文件总数 | **' + report.meta.totalFilesScanned + '** |',
      | 包含中文的文件 | **' + report.meta.totalChineseFiles + '** |',
      | 总替换点数 | **' + report.overview.totalReplacements + '** |',
      | 已有 i18n 调用 | **' + report.overview.existingI18n + '** |',
      | 处理错误 | **' + report.overview.totalErrors + '** |',
      '\n## 📝 详细文件列表\n'
    ];
    
    for (const detail of report.details) {
      lines.push(
        `### ${detail.file}\n`,
        `- **中文统计**: ${detail.stats.totalChinese} 字`,
        `- **需替换**: ${detail.stats.totalReplacements} 处`,
        `- **已有i18n**: ${detail.stats.existingI18n} 处`,
        '\n'
      );
      
      // 显示前20条替换建议
      if (detail.results.length > 0) {
        lines.push('| # | 原始文本 | 建议 Key | 类型 |\n|---|---------|----------|------|');
        detail.results.slice(0, 15).forEach((r, i) => {
          lines.push(`| ${i + 1} | \`${r.rawText.substring(0, 30)}...\` | \`${r.suggestedKey}\` | ${r.type} |`);
        });
        lines.push('\n');
      }
    }
    
    if (report.recommendations.length > 0) {
      lines.push('---\n\n## 💡 改进建议\n');
      for (const rec of report.recommendations) {
        const icon = rec.priority === 'critical' ? '🔴' : rec.priority === 'high' ? '⚠️ ' : 'ℹ️ ';
        lines.push(`${icon} **[${rec.category.toUpperCase()}]** ${rec.message}`);
        if (rec.files) {
          rec.files.forEach(f => {
            lines.push(`   - ${typeof f === 'string' ? f : f.file}${f.count ? ` (${f.count} 处)` : ''}`);
          });
        }
        lines.push('');
      }
    }
    
    return lines.join('\n');
  }
}

// ==================== 主程序入口 ====================

async function main() {
  console.log('========================================');
  console.log('  🌐 国际化改造 - 并行处理工具');
  console.log('========================================\n');
  
  const processor = new ParallelProcessor();
  
  // 1. 扫描文件
  console.log(`📁 扫描目录: ${path.resolve(CONFIG.rootDir, CONFIG.targetDir)}`);
  const allFiles = await processor.scanDirectory(path.resolve(CONFIG.rootDir, CONFIG.targetDir));
  console.log(`   发现 ${allFiles.length} 个 Vue 文件\n`);
  
  // 2. 过滤含中文的文件
  console.log('🔍 筛选包含中文的文件...');
  const chineseFiles = processor.filterChineseFiles(allFiles);
  console.log(`   找到 ${chineseFiles.length} 个需要处理的文件\n`);
  
  if (chineseFiles.length === 0) {
    console.log('✅ 所有文件已完成国际化改造！');
    return;
  }
  
  // 3. 并行处理
  await processor.processAll(chineseFiles);
  
  // 4. 生成报告
  console.log('\n📊 生成处理报告...');
  await processor.saveReport();
  
  // 5. 如果是修复模式，执行自动修复
  if (CONFIG.fix && !CONFIG.dryRun) {
    await processor.fixAll();
  } else if (CONFIG.dryRun) {
    console.log('\n👀 干运行模式，未修改任何文件');
    console.log('   如需实际修改，请添加 --fix 参数');
  }
  
  console.log('\n✨ 处理完成！');
}

// ==================== Worker 线程入口 ====================

if (!isMainThread) {
  // Worker 线程接收任务
  parentPort.on('message', async (data) => {
    const { fileId, filePath } = data;
    const processor = new I18nFileProcessor(filePath);
    const result = await processor.parse();
    parentPort.postMessage({ fileId, result });
  });
} else {
  // 主线程入口
  main().catch(console.error);
}
