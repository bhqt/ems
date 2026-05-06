#!/usr/bin/env python3
"""
测试脚本 - 验证 MQTT 设备模拟工具的基本功能
"""

import sys
from pathlib import Path

# 添加 src 目录到路径
sys.path.insert(0, str(Path(__file__).parent / 'src'))

def test_imports():
    """测试所有模块是否可以正常导入"""
    print("测试模块导入...")
    
    try:
        from mqtt_client import MqttClientWrapper
        print("✓ mqtt_client 导入成功")
    except Exception as e:
        print(f"✗ mqtt_client 导入失败：{e}")
        return False
    
    try:
        from config import ConfigLoader, TemplateLoader
        print("✓ config 导入成功")
    except Exception as e:
        print(f"✗ config 导入失败：{e}")
        return False
    
    try:
        from message_generator import MessageGenerator, TopicBuilder
        print("✓ message_generator 导入成功")
    except Exception as e:
        print(f"✗ message_generator 导入失败：{e}")
        return False
    
    try:
        from single_simulator import SingleMessageSimulator
        print("✓ single_simulator 导入成功")
    except Exception as e:
        print(f"✗ single_simulator 导入失败：{e}")
        return False
    
    try:
        from stress_tester import StressTester
        print("✓ stress_tester 导入成功")
    except Exception as e:
        print(f"✗ stress_tester 导入失败：{e}")
        return False
    
    try:
        from reporters import ReportGenerator
        print("✓ reporters 导入成功")
    except Exception as e:
        print(f"✗ reporters 导入失败：{e}")
        return False
    
    return True


def test_config_loading():
    """测试配置文件加载"""
    print("\n测试配置文件加载...")
    
    try:
        from config import ConfigLoader, TemplateLoader
        
        config_loader = ConfigLoader()
        config = config_loader.load()
        print(f"✓ 主配置加载成功：{config_loader.config_path}")
        
        template_loader = TemplateLoader()
        templates = template_loader.load()
        print(f"✓ 模板配置加载成功：{template_loader.template_path}")
        print(f"  模板数量：{len(templates)}")
        
        return True
    except Exception as e:
        print(f"✗ 配置加载失败：{e}")
        return False


def test_message_generation():
    """测试消息生成"""
    print("\n测试消息生成...")
    
    try:
        from config import TemplateLoader
        from message_generator import MessageGenerator
        
        template_loader = TemplateLoader()
        templates = template_loader.load()
        generator = MessageGenerator(templates)
        
        # 测试电压模板
        message = generator.generate_message('voltage', device_id=1)
        print(f"✓ 电压消息生成成功:")
        print(f"  clientId: {message.get('clientId')}")
        print(f"  value: {message.get('value')}")
        print(f"  createTime: {message.get('createTime')}")
        
        # 测试电流模板
        message = generator.generate_message('current', device_id=2)
        print(f"✓ 电流消息生成成功:")
        print(f"  clientId: {message.get('clientId')}")
        print(f"  value: {message.get('value')}")
        
        # 测试电表综合数据模板
        message = generator.generate_message('emsCarson', device_id=1)
        print(f"✓ 电表综合数据消息生成成功:")
        print(f"  clientId: {message.get('clientId')}")
        print(f"  字段数：{len(message)}")
        
        return True
    except Exception as e:
        print(f"✗ 消息生成测试失败：{e}")
        import traceback
        traceback.print_exc()
        return False


def test_topic_builder():
    """测试主题构建"""
    print("\n测试主题构建...")
    
    try:
        from message_generator import TopicBuilder
        
        topics = TopicBuilder.get_supported_topics()
        print(f"✓ 支持的主题数量：{len(topics)}")
        
        for topic, desc in list(topics.items())[:3]:
            print(f"  - {topic}: {desc}")
        
        # 测试主题构建
        full_topic = TopicBuilder.build_topic('electric/voltage', 'device_001')
        print(f"✓ 主题构建测试：{full_topic}")
        
        return True
    except Exception as e:
        print(f"✗ 主题构建测试失败：{e}")
        return False


def test_report_generation():
    """测试报告生成"""
    print("\n测试报告生成...")
    
    try:
        from reporters import ReportGenerator
        import tempfile
        
        # 创建临时输出目录
        with tempfile.TemporaryDirectory() as tmpdir:
            generator = ReportGenerator(output_dir=tmpdir)
            
            # 模拟测试结果
            test_result = {
                'summary': {
                    'total_messages': 1000,
                    'success': 998,
                    'failed': 2,
                    'success_rate': 99.8,
                    'duration_seconds': 60.5,
                    'throughput': 16.53
                },
                'performance': {
                    'avg_latency_ms': 45.2,
                    'max_latency_ms': 230.5,
                    'min_latency_ms': 12.3
                },
                'time_info': {
                    'start_time': '2026-04-30 10:00:00',
                    'end_time': '2026-04-30 10:01:00'
                },
                'errors': ['Connection timeout', 'Broker error']
            }
            
            test_config = {
                'scenario': 'light',
                'devices': 10,
                'frequency': 1,
                'duration': 60,
                'message_type': 'voltage'
            }
            
            # 生成 Markdown 报告
            md_path = generator.generate_stress_test_report(
                test_result, test_config, 'md', 'test_report'
            )
            print(f"✓ Markdown 报告生成成功：{md_path}")
            
            # 生成 JSON 报告
            json_path = generator.generate_stress_test_report(
                test_result, test_config, 'json', 'test_report'
            )
            print(f"✓ JSON 报告生成成功：{json_path}")
            
            return True
    except Exception as e:
        print(f"✗ 报告生成测试失败：{e}")
        import traceback
        traceback.print_exc()
        return False


def main():
    """主测试函数"""
    print("=" * 60)
    print("MQTT 设备模拟工具 - 功能测试")
    print("=" * 60)
    
    results = []
    
    # 运行所有测试
    results.append(("模块导入", test_imports()))
    results.append(("配置加载", test_config_loading()))
    results.append(("消息生成", test_message_generation()))
    results.append(("主题构建", test_topic_builder()))
    results.append(("报告生成", test_report_generation()))
    
    # 汇总结果
    print("\n" + "=" * 60)
    print("测试结果汇总")
    print("=" * 60)
    
    passed = 0
    failed = 0
    
    for name, result in results:
        status = "✓ 通过" if result else "✗ 失败"
        print(f"{name}: {status}")
        if result:
            passed += 1
        else:
            failed += 1
    
    print("=" * 60)
    print(f"总计：{passed} 通过，{failed} 失败")
    
    if failed == 0:
        print("\n✓ 所有测试通过！工具可以正常使用。")
        return 0
    else:
        print(f"\n✗ 有 {failed} 个测试失败，请检查问题。")
        return 1


if __name__ == '__main__':
    sys.exit(main())
