"""
报告生成器模块
负责生成测试报告（Markdown、JSON 格式）
"""

import json
import os
from datetime import datetime
from typing import Dict, Any, Optional, List
from pathlib import Path


class ReportGenerator:
    """报告生成器"""
    
    def __init__(self, output_dir: str = "output"):
        """
        初始化报告生成器
        
        Args:
            output_dir: 输出目录
        """
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(parents=True, exist_ok=True)
    
    def generate_stress_test_report(self, test_result: Dict[str, Any],
                                   test_config: Dict[str, Any],
                                   format: str = 'md',
                                   filename: Optional[str] = None) -> str:
        """
        生成压力测试报告
        
        Args:
            test_result: 测试结果
            test_config: 测试配置
            format: 报告格式（md/json）
            filename: 输出文件名
            
        Returns:
            str: 报告文件路径
        """
        if filename is None:
            timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
            filename = f"stress_test_{timestamp}"
        
        if format == 'md':
            return self._generate_md_report(test_result, test_config, filename)
        elif format == 'json':
            return self._generate_json_report(test_result, test_config, filename)
        else:
            raise ValueError(f"不支持的报告格式：{format}")
    
    def _generate_md_report(self, test_result: Dict, test_config: Dict, 
                           filename: str) -> str:
        """生成 Markdown 格式报告"""
        summary = test_result.get('summary', {})
        performance = test_result.get('performance', {})
        time_info = test_result.get('time_info', {})
        
        md_content = f"""# MQTT 压力测试报告

## 测试概览
- 测试 ID: stress_{datetime.now().strftime('%Y%m%d_%H%M%S')}
- 测试时间：{time_info.get('start_time', 'N/A')} - {time_info.get('end_time', 'N/A')}
- 测试场景：{test_config.get('scenario', '自定义')}

## 测试配置
| 配置项 | 值 |
|--------|-----|
| 设备数量 | {test_config.get('devices', 'N/A')} |
| 发送频率 | {test_config.get('frequency', 'N/A')} 消息/秒 |
| 测试持续时间 | {test_config.get('duration', 'N/A')} 秒 |
| 消息类型 | {test_config.get('message_type', 'N/A')} |
| MQTT Broker | {test_config.get('mqtt_host', 'N/A')} |

## 测试结果
| 指标 | 值 |
|------|-----|
| 总消息数 | {summary.get('total_messages', 0):,} |
| 成功发送 | {summary.get('success', 0):,} |
| 失败消息 | {summary.get('failed', 0):,} |
| 成功率 | {summary.get('success_rate', 0):.2f}% |
| 实际持续时间 | {summary.get('duration_seconds', 0):.2f} 秒 |
| 平均吞吐量 | {summary.get('throughput', 0):.2f} 消息/秒 |

## 性能指标
| 指标 | 值 |
|------|-----|
| 平均延迟 | {performance.get('avg_latency_ms', 0):.2f}ms |
| 最大延迟 | {performance.get('max_latency_ms', 0):.2f}ms |
| 最小延迟 | {performance.get('min_latency_ms', 0):.2f}ms |

## 错误统计
- 错误总数：{len(test_result.get('errors', []))}
- 错误详情：
"""
        
        # 添加错误列表（最多 20 条）
        errors = test_result.get('errors', [])[:20]
        if errors:
            for i, error in enumerate(errors, 1):
                md_content += f"  {i}. {error}\n"
        else:
            md_content += "  无错误\n"
        
        md_content += f"""
## 测试结论
"""
        
        # 生成测试结论
        success_rate = summary.get('success_rate', 0)
        if success_rate >= 99.9:
            md_content += "✓ **优秀** - 测试表现优秀，系统稳定性良好\n"
        elif success_rate >= 99:
            md_content += "✓ **良好** - 测试表现良好，系统基本稳定\n"
        elif success_rate >= 95:
            md_content += "⚠ **一般** - 测试表现一般，建议优化\n"
        else:
            md_content += "✗ **较差** - 测试表现较差，需要重点关注\n"
        
        md_content += f"""
---
**报告生成时间**: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}  
**工具版本**: v1.0  
**项目名称**: 祝融能源管理系统 MQTT 设备模拟工具
"""
        
        # 保存文件
        filepath = self.output_dir / f"{filename}.md"
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(md_content)
        
        return str(filepath)
    
    def _generate_json_report(self, test_result: Dict, test_config: Dict,
                             filename: str) -> str:
        """生成 JSON 格式报告"""
        report = {
            'report_info': {
                'report_id': f"stress_{datetime.now().strftime('%Y%m%d_%H%M%S')}",
                'generated_at': datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
                'tool_version': 'v1.0',
                'project': '祝融能源管理系统 MQTT 设备模拟工具'
            },
            'test_config': test_config,
            'test_result': test_result
        }
        
        # 保存文件
        filepath = self.output_dir / f"{filename}.json"
        with open(filepath, 'w', encoding='utf-8') as f:
            json.dump(report, f, ensure_ascii=False, indent=2)
        
        return str(filepath)
    
    def generate_single_message_report(self, send_result: Dict[str, Any],
                                      format: str = 'md') -> str:
        """
        生成单条消息发送报告
        
        Args:
            send_result: 发送结果
            format: 报告格式
            
        Returns:
            str: 报告内容（字符串）
        """
        if format == 'md':
            report = f"""# MQTT 消息发送报告

## 发送信息
- 消息 ID: {send_result.get('message_id', 'N/A')}
- 发送时间：{send_result.get('timestamp', 'N/A')}
- 主题：{send_result.get('topic', 'N/A')}
- 状态：{'✓ 成功' if send_result.get('success') else '✗ 失败'}

## 消息内容
```json
{json.dumps(send_result.get('payload', {}), ensure_ascii=False, indent=2)}
```

## 发送结果
- 耗时：{send_result.get('elapsed_time', 0):.2f}ms
- 消息大小：{send_result.get('payload_size', 0)} bytes
"""
            if send_result.get('error'):
                report += f"- 错误信息：{send_result.get('error')}\n"
            
            return report
        else:
            return json.dumps(send_result, ensure_ascii=False, indent=2)
