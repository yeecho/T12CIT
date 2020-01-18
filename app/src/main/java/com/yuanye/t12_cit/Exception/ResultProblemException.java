package com.yuanye.t12_cit.Exception;

public class ResultProblemException extends Exception{
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.print("记录的结果个数与测试的项目数不符");
    }
}
