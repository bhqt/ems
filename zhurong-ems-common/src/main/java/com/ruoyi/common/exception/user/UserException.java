package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

        public UserException(String code, Object... args) {
        super("user", code, args, null);
    }

    public UserException(String code)
    {
        super("user", code, null, null);
    }
    // public UserException(String code, Object[] args)
    // {
    //     super("user", code, args, null);
    // }

    // 添加新的构造方法，直接接收单个参数
    public UserException(String code, Object arg)
    {
        super("user", code, new Object[]{arg}, null);
    }
}
