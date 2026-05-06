"""
压力测试器
用于并发压力测试
"""

import asyncio
import logging
import time
from datetime import datetime
from typing import Dict, Any, Optional, List
from concurrent.futures import ThreadPoolExecutor
import threading

from mqtt_client import MqttClientWrapper
from message_generator import MessageGenerator, TopicBuilder

logger = logging.getLogger(__name__)


class StressTester:
    """压力测试器"""
    
    def __init__(self, mqtt_client: MqttClientWrapper,
                 message_generator: MessageGenerator):
        """
        初始化压力测试器
        
        Args:
            mqtt_client: MQTT 客户端
            message_generator: 消息生成器
        """
        self.mqtt_client = mqtt_client
        self.message_generator = message_generator
        self.topic_builder = TopicBuilder()
        
        # 统计信息
        self.stats = {
            'sent': 0,
            'success': 0,
            'failed': 0,
            'start_time': None,
            'end_time': None,
            'total_elapsed': 0,
            'latencies': [],
            'errors': []
        }
        
        # 控制标志
        self.running = False
        self.paused = False
        self._lock = threading.Lock()
    
    def run_test(self, devices: int, frequency: float, duration: int,
                message_type: str = 'voltage', 
                progress_callback: Optional[callable] = None) -> Dict[str, Any]:
        """
        运行压力测试
        
        Args:
            devices: 设备数量
            frequency: 发送频率（消息/秒）
            duration: 测试持续时间（秒）
            message_type: 消息类型
            progress_callback: 进度回调函数
            
        Returns:
            dict: 测试结果统计
        """
        logger.info(f"开始压力测试 - 设备数：{devices}, 频率：{frequency}Hz, 持续时间：{duration}s")
        
        # 重置统计
        self._reset_stats()
        self.running = True
        self.stats['start_time'] = datetime.now()
        
        try:
            # 创建线程池
            with ThreadPoolExecutor(max_workers=devices) as executor:
                # 提交设备任务
                futures = []
                for device_id in range(1, devices + 1):
                    future = executor.submit(
                        self._device_worker,
                        device_id=device_id,
                        frequency=frequency,
                        duration=duration,
                        message_type=message_type,
                        progress_callback=progress_callback
                    )
                    futures.append(future)
                
                # 等待所有任务完成
                for future in futures:
                    try:
                        future.result()
                    except Exception as e:
                        logger.error(f"设备任务异常：{str(e)}")
                        with self._lock:
                            self.stats['errors'].append(str(e))
        
        except Exception as e:
            logger.error(f"压力测试异常：{str(e)}")
            self.stats['errors'].append(str(e))
        
        finally:
            self.running = False
            self.stats['end_time'] = datetime.now()
            self.stats['total_elapsed'] = (
                self.stats['end_time'] - self.stats['start_time']
            ).total_seconds()
        
        # 生成测试报告
        return self._generate_report()
    
    def _device_worker(self, device_id: int, frequency: float, duration: int,
                      message_type: str, progress_callback: Optional[callable] = None):
        """
        单个设备的工作协程
        
        Args:
            device_id: 设备 ID
            frequency: 发送频率
            duration: 持续时间
            message_type: 消息类型
            progress_callback: 进度回调
        """
        import time
        
        interval = 1.0 / frequency if frequency > 0 else 1.0
        start_time = time.time()
        message_count = 0
        
        # 生成客户端 ID
        client_id = f"device_{device_id:03d}"
        
        while self.running and (time.time() - start_time) < duration:
            if self.paused:
                time.sleep(0.1)
                continue
            
            try:
                # 生成消息
                topic, payload = self._generate_message(message_type, device_id)
                
                # 发送消息
                send_time = time.time()
                result = self.mqtt_client.publish(topic, payload)
                elapsed = (time.time() - send_time) * 1000  # 毫秒
                
                # 更新统计
                with self._lock:
                    self.stats['sent'] += 1
                    if result['success']:
                        self.stats['success'] += 1
                    else:
                        self.stats['failed'] += 1
                        self.stats['errors'].append(result.get('error', '未知错误'))
                    
                    # 记录延迟（最多保留 1000 个）
                    if len(self.stats['latencies']) < 1000:
                        self.stats['latencies'].append(elapsed)
                
                message_count += 1
                
                # 调用进度回调
                if progress_callback:
                    progress_callback(device_id, message_count, elapsed)
                
            except Exception as e:
                logger.error(f"设备 {device_id} 发送消息失败：{str(e)}")
                with self._lock:
                    self.stats['failed'] += 1
                    self.stats['errors'].append(str(e))
            
            # 等待下一次发送
            time.sleep(interval)
    
    def _generate_message(self, message_type: str, device_id: int) -> tuple:
        """
        生成消息
        
        Args:
            message_type: 消息类型
            device_id: 设备 ID
            
        Returns:
            tuple: (topic, payload)
        """
        template_map = {
            'voltage': 'voltage',
            'current': 'current',
            'power': 'power',
            'consumption': 'consumption',
            'water': 'water',
            'emsCarson': 'emsCarson',
            'mixed': None  # 混合类型特殊处理
        }
        
        if message_type == 'mixed':
            # 随机选择一种类型
            import random
            types = ['voltage', 'current', 'power', 'consumption']
            message_type = random.choice(types)
            template_name = template_map[message_type]
        else:
            template_name = template_map.get(message_type, 'voltage')
        
        if template_name:
            # 使用模板生成
            message = self.message_generator.generate_message(template_name, device_id)
            topic = self.message_generator.templates[template_name].get('topic', '')
            
            # 构建完整主题
            client_id = f"device_{device_id:03d}"
            full_topic = self.topic_builder.build_topic(topic, client_id)
            
            return full_topic, message
        else:
            # 默认电压数据
            client_id = f"device_{device_id:03d}"
            return f"electric/voltage/{client_id}", {
                'clientId': client_id,
                'value': round(220.0 + device_id * 0.1, 2),
                'createTime': datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            }
    
    def _reset_stats(self):
        """重置统计信息"""
        self.stats = {
            'sent': 0,
            'success': 0,
            'failed': 0,
            'start_time': None,
            'end_time': None,
            'total_elapsed': 0,
            'latencies': [],
            'errors': []
        }
    
    def _generate_report(self) -> Dict[str, Any]:
        """生成测试报告"""
        # 计算统计数据
        total = self.stats['sent']
        success = self.stats['success']
        failed = self.stats['failed']
        
        success_rate = (success / total * 100) if total > 0 else 0
        
        # 计算延迟统计
        latencies = self.stats['latencies']
        if latencies:
            avg_latency = sum(latencies) / len(latencies)
            max_latency = max(latencies)
            min_latency = min(latencies)
        else:
            avg_latency = max_latency = min_latency = 0
        
        # 计算吞吐量
        duration = self.stats['total_elapsed']
        throughput = total / duration if duration > 0 else 0
        
        return {
            'summary': {
                'total_messages': total,
                'success': success,
                'failed': failed,
                'success_rate': round(success_rate, 2),
                'duration_seconds': round(duration, 2),
                'throughput': round(throughput, 2)
            },
            'performance': {
                'avg_latency_ms': round(avg_latency, 2),
                'max_latency_ms': round(max_latency, 2),
                'min_latency_ms': round(min_latency, 2)
            },
            'time_info': {
                'start_time': self.stats['start_time'].strftime('%Y-%m-%d %H:%M:%S') 
                    if self.stats['start_time'] else None,
                'end_time': self.stats['end_time'].strftime('%Y-%m-%d %H:%M:%S') 
                    if self.stats['end_time'] else None
            },
            'errors': self.stats['errors'][:100]  # 最多返回 100 个错误
        }
    
    def stop(self):
        """停止测试"""
        logger.info("正在停止压力测试...")
        self.running = False
    
    def pause(self):
        """暂停测试"""
        logger.info("暂停压力测试")
        self.paused = True
    
    def resume(self):
        """恢复测试"""
        logger.info("恢复压力测试")
        self.paused = False
    
    def get_realtime_stats(self) -> Dict[str, Any]:
        """获取实时统计"""
        with self._lock:
            elapsed = 0
            if self.stats['start_time']:
                end_time = self.stats['end_time'] if self.stats['end_time'] else datetime.now()
                elapsed = (end_time - self.stats['start_time']).total_seconds()
            
            return {
                'running': self.running,
                'paused': self.paused,
                'sent': self.stats['sent'],
                'success': self.stats['success'],
                'failed': self.stats['failed'],
                'elapsed_seconds': round(elapsed, 2),
                'current_throughput': round(self.stats['sent'] / elapsed, 2) if elapsed > 0 else 0
            }
