package net.xuele.cloudteach.test.service.base;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * ServiceTestBase
 *
 * @author sunxh
 * @date 2015/6/24 0024
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:service-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public abstract class ServiceJunitBase extends AbstractTransactionalJUnit4SpringContextTests {
    @Before
    public void before() {
        String fileName = getSqlFileName();
        if (null != fileName && StringUtils.isNotEmpty(fileName)) {
            executeSqlScript("classpath:h2/" + fileName, false);
        }
    }

    protected String getSqlFileName() {
        return null;
    }

}
