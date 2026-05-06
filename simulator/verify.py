#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
简单验证脚本 - 验证 MQTT 设备模拟工具的基本功能
"""

import sys
import os

# 设置控制台编码为 UTF-8
if sys.platform == 'win32':
    import io
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8', errors='replace')

# 添加 src 目录到路径
sys.path.insert(0, os.path.join(os.path.dirname(__file__), 'src'))

def main():
    print("=" * 60)
    print("MQTT 设备模拟工具 - 功能验证")
    print("=" * 60)
    
    # 测试 1: 导入模块
    print("\n[测试 1] 模块导入...")
    try:
        from mqtt_client import MqttClientWrapper
        print("  [PASS] mqtt_client 导入成功")
    except Exception as e:
        print(f"  [FAIL] mqtt_client 导入失败：{e}")
        return 1
    
    try:
        from config import ConfigLoader, TemplateLoader
        print("  [PASS] config 导入成功")
    except Exception as e:
        print(f"  [FAIL] config 导入失败：{e}")
        return 1
    
    try:
        from message_generator import MessageGenerator, TopicBuilder
        print("  [PASS] message_generator 导入成功")
    except Exception as e:
        print(f"  [FAIL] message_generator 导入失败：{e}")
        return 1
    
    try:
        from single_simulator import SingleMessageSimulator
        print("  [PASS] single_simulator 导入成功")
    except Exception as e:
        print(f"  [FAIL] single_simulator 导入失败：{e}")
        return 1
    
    try:
        from stress_tester import StressTester
        print("  [PASS] stress_tester 导入成功")
    except Exception as e:
        print(f"  [FAIL] stress_tester 导入失败：{e}")
        return 1
    
    try:
        from reporters import ReportGenerator
        print("  [PASS] reporters 导入成功")
    except Exception as e:
        print(f"  [FAIL] reporters 导入失败：{e}")
        return 1
    
    # 测试 2: 配置加载
    print("\n[测试 2] 配置加载...")
    try:
        config_loader = ConfigLoader()
        config = config_loader.load()
        print(f"  [PASS] 主配置加载成功")
        
        template_loader = TemplateLoader()
        templates = template_loader.load()
        print(f"  [PASS] 模板配置加载成功 (共{len(templates)}个模板)")
    except Exception as e:
        print(f"  [FAIL] 配置加载失败：{e}")
        return 1
    
    # 测试 3: 消息生成
    print("\n[测试 3] 消息生成...")
    try:
        generator = MessageGenerator(templates)
        
        # 测试电压模板
        msg = generator.generate_message('voltage', 1)
        print(f"  [PASS] 电压消息生成：{msg.get('clientId')}")
        
        # 测试电流模板
        msg = generator.generate_message('current', 2)
        print(f"  [PASS] 电流消息生成：{msg.get('value')}")
        
        # 测试电表综合数据
        msg = generator.generate_message('emsCarson', 1)
        print(f"  [PASS] 电表综合数据生成：{len(msg)}个字段")
    except Exception as e:
        print(f"  [FAIL] 消息生成失败：{e}")
        return 1
    
    # 测试 4: 主题构建
    print("\n[测试 4] 主题构建...")
    try:
        topics = TopicBuilder.get_supported_topics()
        print(f"  [PASS] 支持{len(topics)}个主题")
        
        topic = TopicBuilder.build_topic('electric/voltage', 'device_001')
        print(f"  [PASS] 主题构建：{topic}")
    except Exception as e:
        print(f"  [FAIL] 主题构建失败：{e}")
        return 1
    
    # 测试 5: 报告生成
    print("\n[测试 5] 报告生成...")
    try:
        import tempfile
        generator = ReportGenerator(output_dir=tempfile.gettempdir())
        
        test_result = {
            'summary': {
                'total_messages': 1000,
                'success': 998,
                'failed': 2,
                'success_rate': 99.8
            }
        }
        test_config = {'scenario': 'light'}
        
        path = generator.generate_stress_test_report(
            test_result, test_config, 'md', 'test'
        )
        print(f"  [PASS] Markdown 报告生成：{os.path.basename(path)}")
    except Exception as e:
        print(f"  [FAIL] 报告生成失败：{e}")
        return 1
    
    # 汇总
    print("\n" + "=" * 60)
    print("所有测试通过！工具可以正常使用。")
    print("=" * 60)
    print("\n下一步:")
    print("  1. 编辑 config/config.yaml 配置 MQTT 连接")
    print("  2. 运行 python main.py ping 测试连接")
    print("  3. 运行 python main.py send 发送消息")
    print("  4. 运行 python main.py stress 进行压力测试")
    print("\n详细帮助：python main.py --help")
    
    return 0

if __name__ == '__main__':
    sys.exit(main())
