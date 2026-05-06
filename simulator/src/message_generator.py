"""
消息生成器模块
负责生成符合后端解析逻辑的 MQTT 消息
"""

import random
import re
import logging
from datetime import datetime
from typing import Dict, Any, Optional, List, Union

logger = logging.getLogger(__name__)


class MessageGenerator:
    """消息生成器"""
    
    def __init__(self, templates: Dict[str, Any]):
        """
        初始化消息生成器
        
        Args:
            templates: 模板字典
        """
        self.templates = templates
    
    def generate_message(self, template_name: str, device_id: int = 1, 
                        custom_data: Optional[Dict] = None) -> Dict[str, Any]:
        """
        生成单条消息
        
        Args:
            template_name: 模板名称
            device_id: 设备 ID
            custom_data: 自定义数据（可选，会覆盖模板生成的数据）
            
        Returns:
            dict: 生成的消息
        """
        template = self.templates.get(template_name)
        if not template:
            raise ValueError(f"模板不存在：{template_name}")
        
        # 生成消息数据
        if template.get('is_array', False):
            # 数组格式消息
            message = self._generate_array_message(template, device_id)
        else:
            # 对象格式消息
            message = self._generate_object_message(template, device_id)
        
        # 合并自定义数据
        if custom_data:
            message.update(custom_data)
        
        return message
    
    def _generate_object_message(self, template: Dict, device_id: int) -> Dict[str, Any]:
        """生成对象格式消息"""
        data_pattern = template.get('data_pattern', {})
        message = {}
        
        for key, value_pattern in data_pattern.items():
            if value_pattern == 'auto':
                # 自动生成时间戳
                if key == 'createTime':
                    message[key] = self._generate_timestamp()
            elif isinstance(value_pattern, str) and value_pattern.startswith('random('):
                # 生成随机值
                message[key] = self._parse_random(value_pattern)
            elif isinstance(value_pattern, str) and '{id' in value_pattern:
                # 格式化设备 ID
                message[key] = self._format_device_id(value_pattern, device_id)
            else:
                # 直接使用值
                message[key] = value_pattern
        
        return message
    
    def _generate_array_message(self, template: Dict, device_id: int) -> List[Dict[str, Any]]:
        """生成数组格式消息"""
        array_items = template.get('array_items', [])
        data_pattern = template.get('data_pattern', {})
        array_size_pattern = template.get('array_size', '1')
        
        # 确定数组大小
        if isinstance(array_size_pattern, str) and array_size_pattern.startswith('random('):
            min_size, max_size = self._parse_random_range(array_size_pattern)
            array_size = random.randint(min_size, max_size)
        else:
            array_size = int(array_size_pattern)
        
        messages = []
        
        # 生成数组项
        if array_items:
            # 使用预定义的数组项模板
            for item_template in array_items:
                item = {}
                
                # 添加 clientId
                if 'clientId' in data_pattern:
                    item['clientId'] = self._format_device_id(
                        data_pattern['clientId'], device_id
                    )
                
                # 添加 deviceType
                if 'deviceType' in item_template:
                    item['deviceType'] = item_template['deviceType']
                
                # 生成 value
                if 'value_pattern' in item_template:
                    value_pattern = item_template['value_pattern']
                    if isinstance(value_pattern, str) and value_pattern.startswith('random('):
                        item['value'] = self._parse_random(value_pattern)
                    else:
                        item['value'] = value_pattern
                
                # 添加 createTime
                if data_pattern.get('createTime') == 'auto':
                    item['createTime'] = self._generate_timestamp()
                
                messages.append(item)
        else:
            # 使用 data_pattern 生成多个相同结构的项
            for i in range(array_size):
                item = self._generate_object_message(template, device_id + i)
                messages.append(item)
        
        return messages
    
    def _generate_timestamp(self) -> str:
        """生成时间戳（格式：yyyy-MM-dd HH:mm:ss）"""
        return datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    
    def _parse_random(self, pattern: str) -> Union[float, int]:
        """
        解析 random 函数
        
        支持格式:
        - random(min, max) - 随机整数
        - random(min, max, decimals) - 随机小数，保留指定小数位
        
        Args:
            pattern: random 函数字符串
            
        Returns:
            随机数
        """
        match = re.match(r'random\(([^)]+)\)', pattern)
        if not match:
            raise ValueError(f"无效的 random 格式：{pattern}")
        
        params = [p.strip() for p in match.group(1).split(',')]
        
        if len(params) < 2:
            raise ValueError(f"random 参数不足：{pattern}")
        
        min_val = float(params[0])
        max_val = float(params[1])
        
        if len(params) >= 3:
            # 保留小数位
            decimals = int(params[2])
            value = random.uniform(min_val, max_val)
            return round(value, decimals)
        else:
            # 整数
            return random.randint(int(min_val), int(max_val))
    
    def _parse_random_range(self, pattern: str) -> tuple:
        """解析 random 范围"""
        match = re.match(r'random\(([^)]+)\)', pattern)
        if not match:
            return (1, 1)
        
        params = [p.strip() for p in match.group(1).split(',')]
        return (int(params[0]), int(params[1]))
    
    def _format_device_id(self, pattern: str, device_id: int) -> str:
        """
        格式化设备 ID
        
        支持格式:
        - device_{id} - 直接替换
        - device_{id:03d} - 格式化为 3 位数字
        - device_{id:04d} - 格式化为 4 位数字
        
        Args:
            pattern: 格式字符串
            device_id: 设备 ID
            
        Returns:
            格式化后的字符串
        """
        # 匹配 {id} 或 {id:格式}
        match = re.search(r'\{id(?::([^}]+))?\}', pattern)
        if not match:
            return pattern
        
        format_spec = match.group(1) if match.group(1) else ''
        
        try:
            if format_spec:
                # 使用指定格式
                formatted_id = format(device_id, format_spec)
            else:
                # 直接转换为字符串
                formatted_id = str(device_id)
            
            return pattern.replace(match.group(0), formatted_id)
        except Exception as e:
            logger.error(f"格式化设备 ID 失败：{str(e)}")
            return pattern.replace(match.group(0), str(device_id))
    
    def generate_random_value(self, min_val: float, max_val: float, 
                             decimals: int = 2) -> float:
        """
        生成随机值（便捷方法）
        
        Args:
            min_val: 最小值
            max_val: 最大值
            decimals: 小数位数
            
        Returns:
            随机值
        """
        value = random.uniform(min_val, max_val)
        return round(value, decimals)


class TopicBuilder:
    """主题构建器"""
    
    @staticmethod
    def build_topic(base_topic: str, client_id: Optional[str] = None) -> str:
        """
        构建完整的 MQTT 主题
        
        Args:
            base_topic: 基础主题
            client_id: 设备 ID
            
        Returns:
            完整的主题
        """
        if client_id and '{clientId}' in base_topic:
            return base_topic.replace('{clientId}', client_id)
        elif client_id and not base_topic.endswith('/'):
            # 如果主题中没有{clientId}占位符，但有 client_id，则追加
            return f"{base_topic}/{client_id}"
        else:
            return base_topic
    
    @staticmethod
    def get_supported_topics() -> Dict[str, str]:
        """
        获取支持的主题列表
        
        Returns:
            dict: 主题说明字典
        """
        return {
            'electric/emsCarson': '电表综合数据',
            'electric/all/{clientId}': '电表所有数据（数组）',
            'electric/voltage/{clientId}': '电压数据',
            'electric/current/{clientId}': '电流数据',
            'electric/power/{clientId}': '功率数据',
            'electric/consumption/{clientId}': '电能数据',
            'water/consumption/{clientId}': '水表数据（数组）'
        }
