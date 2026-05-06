"""
配置加载模块
负责加载和管理 YAML 配置文件
"""

import os
import yaml
import logging
from typing import Dict, Any, Optional
from pathlib import Path

logger = logging.getLogger(__name__)


class ConfigLoader:
    """配置文件加载器"""
    
    def __init__(self, config_path: Optional[str] = None):
        """
        初始化配置加载器
        
        Args:
            config_path: 配置文件路径（可选）
        """
        if config_path is None:
            # 默认配置文件路径
            base_dir = Path(__file__).parent.parent
            config_path = base_dir / 'config' / 'config.yaml'
        
        self.config_path = Path(config_path)
        self.config = {}
        
    def load(self) -> Dict[str, Any]:
        """
        加载配置文件
        
        Returns:
            dict: 配置字典
        """
        if not self.config_path.exists():
            logger.error(f"配置文件不存在：{self.config_path}")
            raise FileNotFoundError(f"配置文件不存在：{self.config_path}")
        
        try:
            with open(self.config_path, 'r', encoding='utf-8') as f:
                self.config = yaml.safe_load(f)
            
            logger.info(f"✓ 配置文件加载成功：{self.config_path}")
            return self.config
            
        except Exception as e:
            logger.error(f"✗ 配置文件加载失败：{str(e)}")
            raise
    
    def get(self, key: str, default: Any = None) -> Any:
        """
        获取配置值（支持点分隔的嵌套键）
        
        Args:
            key: 配置键（如：mqtt.host）
            default: 默认值
            
        Returns:
            配置值
        """
        keys = key.split('.')
        value = self.config
        
        for k in keys:
            if isinstance(value, dict) and k in value:
                value = value[k]
            else:
                return default
        
        return value
    
    def get_mqtt_config(self) -> Dict[str, Any]:
        """获取 MQTT 连接配置"""
        return self.config.get('mqtt', {})
    
    def get_logging_config(self) -> Dict[str, Any]:
        """获取日志配置"""
        return self.config.get('logging', {})
    
    def get_stress_test_config(self) -> Dict[str, Any]:
        """获取压力测试配置"""
        return self.config.get('stress_test', {})
    
    def get_scenario(self, scenario_name: str) -> Optional[Dict[str, Any]]:
        """
        获取指定压力测试场景配置
        
        Args:
            scenario_name: 场景名称（light/medium/heavy）
            
        Returns:
            场景配置字典
        """
        scenarios = self.get('stress_test.scenarios', {})
        return scenarios.get(scenario_name)


class TemplateLoader:
    """消息模板加载器"""
    
    def __init__(self, template_path: Optional[str] = None):
        """
        初始化模板加载器
        
        Args:
            template_path: 模板文件路径（可选）
        """
        if template_path is None:
            base_dir = Path(__file__).parent.parent
            template_path = base_dir / 'config' / 'templates.yaml'
        
        self.template_path = Path(template_path)
        self.templates = {}
        
    def load(self) -> Dict[str, Any]:
        """
        加载模板文件
        
        Returns:
            dict: 模板字典
        """
        if not self.template_path.exists():
            logger.error(f"模板文件不存在：{self.template_path}")
            raise FileNotFoundError(f"模板文件不存在：{self.template_path}")
        
        try:
            with open(self.template_path, 'r', encoding='utf-8') as f:
                config = yaml.safe_load(f)
                self.templates = config.get('templates', {})
            
            logger.info(f"✓ 模板文件加载成功：{self.template_path}")
            return self.templates
            
        except Exception as e:
            logger.error(f"✗ 模板文件加载失败：{str(e)}")
            raise
    
    def get_template(self, name: str) -> Optional[Dict[str, Any]]:
        """
        获取指定模板
        
        Args:
            name: 模板名称
            
        Returns:
            模板配置字典
        """
        return self.templates.get(name)
    
    def list_templates(self) -> list:
        """
        获取所有模板列表
        
        Returns:
            list: 模板名称列表
        """
        return list(self.templates.keys())


def setup_logging(logging_config: Dict[str, Any]):
    """
    配置日志系统
    
    Args:
        logging_config: 日志配置字典
    """
    level = logging.getLevelName(logging_config.get('level', 'INFO'))
    log_file = logging_config.get('file', 'logs/mqtt_simulator.log')
    log_format = logging_config.get('format', '%(asctime)s - %(levelname)s - %(message)s')
    
    # 创建日志目录
    log_dir = os.path.dirname(log_file)
    if log_dir and not os.path.exists(log_dir):
        os.makedirs(log_dir)
    
    # 配置日志
    logging.basicConfig(
        level=level,
        format=log_format,
        handlers=[
            logging.FileHandler(log_file, encoding='utf-8'),
            logging.StreamHandler()
        ]
    )
    
    logger = logging.getLogger(__name__)
    logger.info(f"✓ 日志系统初始化完成，日志文件：{log_file}")
