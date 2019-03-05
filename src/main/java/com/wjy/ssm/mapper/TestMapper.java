package com.wjy.ssm.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author WangJinYi
 *         2018/8/22
 */
public interface TestMapper {

    /**
     * select 1 from dual
     * @return 1
     */
    int test();

    /**
     * update emp t set t.comm = 1 where t.empno = 7369;
     * @return 1
     */
    int testTransaction();

    /**
     * select * from emp
     * @return List
     */
    List<Map<String, Object>> testPageHelper();
}
