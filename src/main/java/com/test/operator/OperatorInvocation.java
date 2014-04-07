package com.test.operator;

import java.util.ArrayList;
import java.util.List;

public class OperatorInvocation
{
    private List<ITuanOperator> operators = null;
    private int index = -1;
    
    public OperatorInvocation()
    {
        operators = new ArrayList<ITuanOperator>();
        operators.add(new MeituanOperator());
        operators.add(new DianpingOperator());
        operators.add(new NuomiOperator());
        operators.add(new TuangouwangOperator());
    }
    
    public Business proceed(String url) throws Exception
    {
        if(index < operators.size() - 1)
        {
            return operators.get(++index).parse(url, this);
        }
        return null;
    }
    
    public void init()
    {
        index = -1;
    }
    
}
