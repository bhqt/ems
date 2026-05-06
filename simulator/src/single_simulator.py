"""
单消息模拟器
用于发送单条 MQTT 消息进行测试
"""

import logging
import json
from typing import Dict, Any, Optional
from mqtt_client import MqttClientWrapper
from message_generator import MessageGenerator, TopicBuilder

logger = logging.getLogger(__name__)


class SingleMessageSimulator:
    """单消息模拟器"""
    
    def __init__(self, mqtt_client: MqttClientWrapper, 
                 message_generator: MessageGenerator):
        """
        初始化单消息模拟器
        
        Args:
            mqtt_client: MQTT 客户端
            message_generator: 消息生成器
        """
        self.mqtt_client = mqtt_client
        self.message_generator = message_generator
        self.topic_builder = TopicBuilder()
    
    def send_message(self, topic: str, data: Optional[Dict] = None,
                    client_id: Optional[str] = None,
                    template_name: Optional[str] = None,
                    device_id: int = 1,
                    qos: Optional[int] = None,
                    retained: Optional[bool] = None) -> Dict[str, Any]:
        """
        发送单条消息
        
        Args:
            topic: MQTT 主题
            data: 消息数据（可选，与 template 二选一）
            client_id: 设备 ID
            template_name: 模板名称（可选，与 data 二选一）
            device_id: 设备 ID 编号（用于模板）
            qos: QoS 级别
            retained: 是否保留消息
            
        Returns:
            dict: 发送结果
        """
        import time
        
        # 构建完整主题
        full_topic = self.topic_builder.build_topic(topic, client_id)
        
        # 生成或准备消息数据
        if data:
            message_data = data.copy()
        elif template_name:
            message_data = self.message_generator.generate_message(
                template_name, device_id
            )
        else:
            return {
                'success': False,
                'error': '必须提供 data 或 template_name 参数'
            }
        
        # 自动补充 createTime（如果没有）
        if 'createTime' not in message_data or not message_data['createTime']:
            message_data['createTime'] = self._generate_timestamp()
        
        logger.info(f"准备发送消息 - 主题：{full_topic}")
        logger.debug(f"消息数据：{json.dumps(message_data, ensure_ascii=False)}")
        
        # 发送消息
        start_time = time.time()
        result = self.mqtt_client.publish(
            topic=full_topic,
            payload=message_data,
            qos=qos,
            retained=retained
        )
        elapsed_time = (time.time() - start_time) * 1000
        
        # 记录结果
        if result['success']:
            logger.info(
                f"✓ 消息发送成功 - ID: {result['message_id']}, "
                f"耗时：{elapsed_time:.2f}ms, "
                f"大小：{result.get('payload_size', 0)} bytes"
            )
        else:
            logger.error(f"✗ 消息发送失败 - 错误：{result.get('error', '未知错误')}")
        
        # 返回详细结果
        return {
            'success': result['success'],
            'topic': full_topic,
            'message_id': result.get('message_id'),
            'payload': message_data,
            'payload_size': result.get('payload_size', 0),
            'elapsed_time': elapsed_time,
            'error': result.get('error'),
            'timestamp': self._generate_timestamp()
        }
    
    def send_with_template(self, template_name: str, client_id: str,
                          device_id: int = 1, **kwargs) -> Dict[str, Any]:
        """
        使用模板发送消息
        
        Args:
            template_name: 模板名称
            client_id: 设备 ID
            device_id: 设备编号
            **kwargs: 其他参数
            
        Returns:
            dict: 发送结果
        """
        template = self.message_generator.templates.get(template_name)
        if not template:
            return {
                'success': False,
                'error': f"模板不存在：{template_name}"
            }
        
        topic = template.get('topic', '')
        
        return self.send_message(
            topic=topic,
            client_id=client_id,
            template_name=template_name,
            device_id=device_id,
            **kwargs
        )
    
    def send_custom(self, topic: str, client_id: str, 
                   custom_data: Dict, **kwargs) -> Dict[str, Any]:
        """
        发送自定义消息
        
        Args:
            topic: MQTT 主题
            client_id: 设备 ID
            custom_data: 自定义数据
            **kwargs: 其他参数
            
        Returns:
            dict: 发送结果
        """
        return self.send_message(
            topic=topic,
            client_id=client_id,
            data=custom_data,
            **kwargs
        )
    
    def _generate_timestamp(self) -> str:
        """生成时间戳"""
        from datetime import datetime
        return datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    
    def validate_message(self, data: Dict, template_name: Optional[str] = None) -> Dict[str, Any]:
        """
        验证消息数据
        
        Args:
            data: 消息数据
            template_name: 模板名称（可选）
            
        Returns:
            dict: 验证结果 {valid: bool, errors: list, warnings: list}
        """
        errors = []
        warnings = []
        
        # 检查必需字段
        if 'clientId' not in data:
            errors.append("缺少必需字段：clientId")
        
        # 检查数据类型
        if 'value' in data:
            if not isinstance(data['value'], (int, float)):
                errors.append("字段 'value' 必须是数字类型")
        
        # 检查时间戳格式
        if 'createTime' in data:
            import re
            pattern = r'^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$'
            if not re.match(pattern, str(data['createTime'])):
                warnings.append("createTime 格式建议为：yyyy-MM-dd HH:mm:ss")
        
        # 模板特定验证
        if template_name:
            template = self.message_generator.templates.get(template_name)
            if template and template.get('is_array', False):
                if not isinstance(data, list):
                    errors.append(f"模板 '{template_name}' 需要数组格式的数据")
        
        return {
            'valid': len(errors) == 0,
            'errors': errors,
            'warnings': warnings
        }
