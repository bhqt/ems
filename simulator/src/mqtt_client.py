"""
MQTT 客户端封装模块
提供 MQTT 连接、发布、订阅等功能
"""

import logging
import os
from typing import Optional, Dict, Any
import paho.mqtt.client as mqtt
from paho.mqtt.client import MQTTMessage

logger = logging.getLogger(__name__)


class MqttClientWrapper:
    """MQTT 客户端封装类"""
    
    def __init__(self, config: Dict[str, Any]):
        """
        初始化 MQTT 客户端
        
        Args:
            config: MQTT 连接配置字典
        """
        self.config = config
        self.host = config.get('host', 'tcp://localhost:1883')
        self.username = config.get('username', '')
        self.password = config.get('password', '')
        self.client_id = config.get('client_id', 'mqtt-simulator')
        self.timeout = config.get('timeout', 120)
        self.keepalive = config.get('keepalive', 360)
        self.qos = config.get('qos', 0)
        self.retain = config.get('retain', False)
        
        # 替换环境变量
        self.client_id = self._replace_env_vars(self.client_id)
        
        # 创建 MQTT 客户端
        self.client = mqtt.Client(
            client_id=self.client_id,
            clean_session=True
        )
        
        # 设置用户名和密码
        if self.username and self.password:
            self.client.username_pw_set(self.username, self.password)
        
        # 设置回调
        self.client.on_connect = self._on_connect
        self.client.on_disconnect = self._on_disconnect
        self.client.on_publish = self._on_publish
        self.client.on_message = self._on_message
        
        # 连接状态
        self.connected = False
        self.message_count = 0
        
    def _replace_env_vars(self, text: str) -> str:
        """替换字符串中的环境变量 ${VAR_NAME}"""
        import re
        pattern = r'\$\{([^}]+)\}'
        
        def replace(match):
            env_var = match.group(1)
            return os.environ.get(env_var, match.group(0))
        
        return re.sub(pattern, replace, text)
    
    def _on_connect(self, client, userdata, flags, rc):
        """连接成功回调"""
        if rc == 0:
            self.connected = True
            logger.info(f"✓ MQTT 连接成功：{self.host}")
        else:
            self.connected = False
            logger.error(f"✗ MQTT 连接失败，错误代码：{rc}")
    
    def _on_disconnect(self, client, userdata, rc):
        """断开连接回调"""
        self.connected = False
        if rc != 0:
            logger.warning(f"⚠ MQTT 非正常断开，错误代码：{rc}")
        else:
            logger.info("✓ MQTT 正常断开连接")
    
    def _on_publish(self, client, userdata, mid):
        """消息发布成功回调"""
        self.message_count += 1
        logger.debug(f"消息发布成功，ID: {mid}, 总消息数：{self.message_count}")
    
    def _on_message(self, client, userdata, msg: MQTTMessage):
        """接收消息回调（用于订阅模式）"""
        logger.debug(f"收到消息 - 主题：{msg.topic}, 内容：{msg.payload}")
    
    def connect(self) -> bool:
        """
        连接到 MQTT Broker
        
        Returns:
            bool: 连接是否成功
        """
        try:
            # 解析主机地址（移除 tcp:// 前缀）
            host_parts = self.host.replace('tcp://', '').split(':')
            host = host_parts[0]
            port = int(host_parts[1]) if len(host_parts) > 1 else 1883
            
            logger.info(f"正在连接到 MQTT Broker: {self.host}...")
            self.client.connect(host, port, keepalive=self.keepalive)
            self.client.loop_start()
            
            # 等待连接（最多 10 秒）
            import time
            wait_count = 0
            while not self.connected and wait_count < 10:
                time.sleep(1)
                wait_count += 1
            
            return self.connected
            
        except Exception as e:
            logger.error(f"✗ MQTT 连接失败：{str(e)}")
            return False
    
    def disconnect(self):
        """断开 MQTT 连接"""
        try:
            self.client.loop_stop()
            self.client.disconnect()
            logger.info("✓ 已断开 MQTT 连接")
        except Exception as e:
            logger.error(f"断开连接时出错：{str(e)}")
    
    def publish(self, topic: str, payload: Dict, qos: Optional[int] = None, 
                retained: Optional[bool] = None) -> Dict[str, Any]:
        """
        发布 MQTT 消息
        
        Args:
            topic: MQTT 主题
            payload: 消息内容（字典）
            qos: QoS 级别（可选，默认使用配置值）
            retained: 是否保留消息（可选，默认使用配置值）
            
        Returns:
            dict: 发布结果 {success: bool, message_id: int, error: str}
        """
        import json
        import time
        
        if not self.connected:
            return {
                'success': False,
                'message_id': None,
                'error': 'MQTT 未连接'
            }
        
        try:
            # 序列化消息
            payload_str = json.dumps(payload, ensure_ascii=False)
            
            # 使用指定的 QoS 和 retain，否则使用默认值
            qos = qos if qos is not None else self.qos
            retained = retained if retained is not None else self.retain
            
            # 发布消息
            start_time = time.time()
            result = self.client.publish(topic, payload_str, qos=qos, retain=retained)
            
            # 等待发布完成（最多 5 秒）
            result.wait_for_publish(timeout=5)
            
            elapsed_time = (time.time() - start_time) * 1000  # 毫秒
            
            if result.is_published():
                logger.debug(f"✓ 消息发布成功 - 主题：{topic}, 耗时：{elapsed_time:.2f}ms")
                return {
                    'success': True,
                    'message_id': result.mid,
                    'error': None,
                    'elapsed_time': elapsed_time,
                    'payload_size': len(payload_str)
                }
            else:
                logger.error(f"✗ 消息发布失败 - 主题：{topic}")
                return {
                    'success': False,
                    'message_id': result.mid,
                    'error': '消息未成功发布'
                }
                
        except Exception as e:
            logger.error(f"✗ 消息发布异常：{str(e)}")
            return {
                'success': False,
                'message_id': None,
                'error': str(e)
            }
    
    def subscribe(self, topic: str, qos: Optional[int] = None):
        """
        订阅 MQTT 主题
        
        Args:
            topic: 主题
            qos: QoS 级别
        """
        qos = qos if qos is not None else self.qos
        try:
            self.client.subscribe(topic, qos=qos)
            logger.info(f"✓ 已订阅主题：{topic}")
        except Exception as e:
            logger.error(f"订阅主题失败：{str(e)}")
    
    def is_connected(self) -> bool:
        """检查是否已连接"""
        return self.connected
    
    def get_stats(self) -> Dict[str, Any]:
        """获取客户端统计信息"""
        return {
            'connected': self.connected,
            'message_count': self.message_count,
            'client_id': self.client_id
        }
