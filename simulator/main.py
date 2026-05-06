#!/usr/bin/env python3
"""
MQTT 设备模拟工具 - 主入口
祝融能源管理系统

用法:
    python mqtt_simulator.py [命令] [选项]

命令:
    send      发送单条消息
    stress    压力测试
    topics    查看支持的主题
    templates 查看消息模板
    ping      测试 MQTT 连接
    validate  验证配置
"""

import sys
import os
import logging
import json
import click
from pathlib import Path

# 添加 src 目录到路径
sys.path.insert(0, str(Path(__file__).parent / 'src'))

from mqtt_client import MqttClientWrapper
from config import ConfigLoader, TemplateLoader, setup_logging
from message_generator import MessageGenerator, TopicBuilder
from single_simulator import SingleMessageSimulator
from stress_tester import StressTester
from reporters import ReportGenerator

# 创建全局 logger
logger = logging.getLogger(__name__)


@click.group()
@click.option('--config', '-c', default=None, help='配置文件路径')
@click.option('--log-level', default='INFO', 
              type=click.Choice(['DEBUG', 'INFO', 'WARNING', 'ERROR']),
              help='日志级别')
@click.version_option(version='1.0.0', prog_name='MQTT 设备模拟工具')
@click.pass_context
def cli(ctx, config, log_level):
    """MQTT 设备模拟工具 - 祝融能源管理系统"""
    ctx.ensure_object(dict)
    
    # 加载配置
    try:
        config_loader = ConfigLoader(config)
        ctx.obj['config'] = config_loader.load()
        ctx.obj['config_loader'] = config_loader
    except Exception as e:
        click.echo(f"✗ 配置文件加载失败：{str(e)}")
        sys.exit(1)
    
    # 设置日志
    logging_config = ctx.obj['config'].get('logging', {})
    if log_level:
        logging_config['level'] = log_level
    setup_logging(logging_config)


@cli.command()
@click.option('--topic', '-t', required=True, help='MQTT 主题')
@click.option('--client-id', '-cid', default=None, help='设备 ID')
@click.option('--data', '-d', default=None, help='JSON 格式的消息数据')
@click.option('--template', '-tpl', default=None, help='使用模板（模板名称）')
@click.option('--device-id', '-did', default=1, type=int, help='设备编号（用于模板）')
@click.option('--qos', '-q', default=0, type=click.IntRange(0, 2), help='QoS 级别')
@click.option('--retain', '-r', is_flag=True, help='保留消息')
@click.option('--dry-run', '-n', is_flag=True, help='空运行（不实际发送）')
@click.pass_context
def send(ctx, topic, client_id, data, template, device_id, qos, retain, dry_run):
    """发送单条 MQTT 消息"""
    config = ctx.obj['config']
    
    # 解析 JSON 数据
    data_dict = None
    if data:
        try:
            data_dict = json.loads(data)
        except json.JSONDecodeError as e:
            click.echo(f"✗ JSON 数据格式错误：{str(e)}")
            sys.exit(1)
    
    # 加载模板
    template_loader = TemplateLoader()
    templates = template_loader.load()
    message_generator = MessageGenerator(templates)
    
    # 创建 MQTT 客户端
    mqtt_config = config.get('mqtt', {})
    mqtt_client = MqttClientWrapper(mqtt_config)
    
    try:
        # 连接 MQTT
        if not mqtt_client.connect():
            click.echo("✗ MQTT 连接失败")
            sys.exit(1)
        
        # 创建模拟器
        simulator = SingleMessageSimulator(mqtt_client, message_generator)
        
        if dry_run:
            # 空运行 - 仅验证
            click.echo("ℹ 空运行模式 - 不实际发送消息")
            if data_dict:
                message = data_dict
            elif template:
                message = message_generator.generate_message(template, device_id)
            else:
                click.echo("✗ 必须提供 --data 或 --template 参数")
                sys.exit(1)
            
            click.echo(f"✓ 消息验证通过")
            click.echo(f"主题：{topic}")
            click.echo(f"数据：{json.dumps(message, ensure_ascii=False)}")
        else:
            # 实际发送
            result = simulator.send_message(
                topic=topic,
                data=data_dict,
                client_id=client_id,
                template_name=template,
                device_id=device_id,
                qos=qos,
                retained=retain
            )
            
            if result['success']:
                click.echo(f"✓ 消息发送成功")
                click.echo(f"消息 ID: {result['message_id']}")
                click.echo(f"主题：{result['topic']}")
                click.echo(f"耗时：{result['elapsed_time']:.2f}ms")
                click.echo(f"大小：{result['payload_size']} bytes")
            else:
                click.echo(f"✗ 消息发送失败：{result.get('error', '未知错误')}")
                sys.exit(1)
    
    finally:
        mqtt_client.disconnect()


@cli.command()
@click.option('--scenario', '-s', default='light', 
              type=click.Choice(['light', 'medium', 'heavy']),
              help='测试场景')
@click.option('--devices', '-d', default=None, type=int, help='设备数量')
@click.option('--frequency', '-f', default=None, type=float, help='发送频率（消息/秒）')
@click.option('--duration', '-dur', default=None, type=int, help='测试持续时间（秒）')
@click.option('--message-type', '-mt', default='voltage', help='消息类型')
@click.option('--output', '-o', default=None, help='输出报告文件')
@click.option('--format', 'report_format', default='md', 
              type=click.Choice(['md', 'json']), help='报告格式')
@click.pass_context
def stress(ctx, scenario, devices, frequency, duration, message_type, output, report_format):
    """并发压力测试"""
    config = ctx.obj['config']
    
    # 获取场景配置或自定义配置
    if devices is None:
        scenario_config = ctx.obj['config_loader'].get_scenario(scenario)
        if scenario_config:
            devices = scenario_config.get('devices', 10)
            frequency = scenario_config.get('frequency', 1)
            duration = scenario_config.get('duration', 60)
            message_type = scenario_config.get('message_type', 'voltage')
    
    # 加载模板
    template_loader = TemplateLoader()
    templates = template_loader.load()
    message_generator = MessageGenerator(templates)
    
    # 创建 MQTT 客户端
    mqtt_config = config.get('mqtt', {})
    mqtt_client = MqttClientWrapper(mqtt_config)
    
    try:
        # 连接 MQTT
        if not mqtt_client.connect():
            click.echo("✗ MQTT 连接失败")
            sys.exit(1)
        
        # 创建压力测试器
        tester = StressTester(mqtt_client, message_generator)
        
        # 进度回调
        def progress_callback(device_id, count, latency):
            if count % 10 == 0:  # 每 10 条消息显示一次
                stats = tester.get_realtime_stats()
                click.echo(
                    f"\r发送中... {stats['sent']} 条，"
                    f"成功率：{stats['success']/stats['sent']*100:.1f}%"
                    f" ({stats['current_throughput']:.1f} msg/s)",
                    nl=False
                )
        
        click.echo(f"开始压力测试 - 设备数：{devices}, 频率：{frequency}Hz, 持续时间：{duration}s")
        click.echo("=" * 60)
        
        # 运行测试
        result = tester.run_test(
            devices=devices,
            frequency=frequency,
            duration=duration,
            message_type=message_type,
            progress_callback=progress_callback
        )
        
        click.echo("\n" + "=" * 60)
        click.echo("✓ 压力测试完成")
        
        # 显示结果
        summary = result.get('summary', {})
        click.echo(f"总消息数：{summary.get('total_messages', 0):,}")
        click.echo(f"成功：{summary.get('success', 0):,}")
        click.echo(f"失败：{summary.get('failed', 0):,}")
        click.echo(f"成功率：{summary.get('success_rate', 0):.2f}%")
        click.echo(f"平均吞吐量：{summary.get('throughput', 0):.2f} msg/s")
        
        performance = result.get('performance', {})
        click.echo(f"平均延迟：{performance.get('avg_latency_ms', 0):.2f}ms")
        click.echo(f"最大延迟：{performance.get('max_latency_ms', 0):.2f}ms")
        click.echo(f"最小延迟：{performance.get('min_latency_ms', 0):.2f}ms")
        
        # 生成报告
        if output or report_format:
            report_gen = ReportGenerator()
            test_config = {
                'scenario': scenario,
                'devices': devices,
                'frequency': frequency,
                'duration': duration,
                'message_type': message_type,
                'mqtt_host': mqtt_config.get('host', 'N/A')
            }
            
            report_formats = report_format.split(',') if report_format else ['md']
            for fmt in report_formats:
                filepath = report_gen.generate_stress_test_report(
                    result, test_config, fmt.strip(), output
                )
                click.echo(f"✓ 报告已保存：{filepath}")
    
    except KeyboardInterrupt:
        click.echo("\n⚠ 测试被用户中断")
        tester.stop()
    except Exception as e:
        click.echo(f"✗ 测试异常：{str(e)}")
        logger.exception("测试异常")
        sys.exit(1)
    finally:
        mqtt_client.disconnect()


@cli.command()
def topics():
    """查看支持的 MQTT 主题"""
    topics = TopicBuilder.get_supported_topics()
    
    click.echo("支持的 MQTT 主题:")
    click.echo("=" * 60)
    click.echo(f"{'主题':<35} {'说明':<25}")
    click.echo("-" * 60)
    
    for topic, desc in topics.items():
        click.echo(f"{topic:<35} {desc:<25}")
    
    click.echo("=" * 60)


@cli.command()
@click.option('--name', '-n', default=None, help='模板名称（查看特定模板详情）')
def templates(name):
    """查看消息模板"""
    template_loader = TemplateLoader()
    templates = template_loader.load()
    
    if name:
        # 查看特定模板
        template = templates.get(name)
        if template:
            click.echo(f"模板：{name}")
            click.echo(f"说明：{template.get('description', 'N/A')}")
            click.echo(f"主题：{template.get('topic', 'N/A')}")
            click.echo(f"数组格式：{'是' if template.get('is_array') else '否'}")
            click.echo(f"\n数据模式:")
            click.echo(json.dumps(template.get('data_pattern', {}), indent=2, ensure_ascii=False))
        else:
            click.echo(f"✗ 模板不存在：{name}")
    else:
        # 列出所有模板
        click.echo("可用的消息模板:")
        click.echo("=" * 60)
        click.echo(f"{'模板名称':<20} {'说明':<30}")
        click.echo("-" * 60)
        
        for tpl_name, tpl_config in templates.items():
            desc = tpl_config.get('description', 'N/A')
            click.echo(f"{tpl_name:<20} {desc:<30}")
        
        click.echo("=" * 60)


@cli.command()
@click.pass_context
def ping(ctx):
    """测试 MQTT 连接"""
    config = ctx.obj['config']
    mqtt_config = config.get('mqtt', {})
    
    click.echo(f"正在测试 MQTT 连接...")
    click.echo(f"主机：{mqtt_config.get('host', 'N/A')}")
    click.echo(f"用户名：{mqtt_config.get('username', 'N/A')}")
    
    mqtt_client = MqttClientWrapper(mqtt_config)
    
    try:
        if mqtt_client.connect():
            click.echo("✓ MQTT 连接成功")
            stats = mqtt_client.get_stats()
            click.echo(f"客户端 ID: {stats['client_id']}")
            click.echo(f"连接状态：已连接")
        else:
            click.echo("✗ MQTT 连接失败")
            sys.exit(1)
    finally:
        mqtt_client.disconnect()


@cli.command()
@click.pass_context
def validate(ctx):
    """验证配置文件"""
    config = ctx.obj['config']
    
    click.echo("验证配置...")
    click.echo("=" * 60)
    
    # 验证 MQTT 配置
    mqtt_config = config.get('mqtt', {})
    errors = []
    warnings = []
    
    if not mqtt_config.get('host'):
        errors.append("缺少必需配置：mqtt.host")
    else:
        click.echo(f"✓ MQTT 主机：{mqtt_config.get('host')}")
    
    if not mqtt_config.get('username'):
        warnings.append("建议配置：mqtt.username")
    else:
        click.echo(f"✓ MQTT 用户名：{mqtt_config.get('username')}")
    
    if not mqtt_config.get('password'):
        warnings.append("建议配置：mqtt.password")
    else:
        click.echo(f"✓ MQTT 密码：已配置")
    
    # 验证日志配置
    logging_config = config.get('logging', {})
    if logging_config:
        click.echo(f"✓ 日志级别：{logging_config.get('level', 'INFO')}")
        click.echo(f"✓ 日志文件：{logging_config.get('file', 'logs/mqtt_simulator.log')}")
    
    # 验证压力测试配置
    stress_config = config.get('stress_test', {})
    if stress_config:
        scenarios = stress_config.get('scenarios', {})
        click.echo(f"✓ 压力测试场景：{len(scenarios)} 个")
        for name in scenarios.keys():
            click.echo(f"  - {name}")
    
    click.echo("=" * 60)
    
    if errors:
        click.echo("✗ 发现错误:")
        for error in errors:
            click.echo(f"  - {error}")
        sys.exit(1)
    
    if warnings:
        click.echo("⚠ 警告:")
        for warning in warnings:
            click.echo(f"  - {warning}")
    
    click.echo("✓ 配置验证通过")


def main():
    """主函数"""
    try:
        cli(obj={})
    except Exception as e:
        click.echo(f"✗ 程序异常：{str(e)}")
        logger.exception("程序异常")
        sys.exit(1)


if __name__ == '__main__':
    main()
