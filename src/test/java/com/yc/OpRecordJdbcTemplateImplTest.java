package com.yc;

import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.dao.OpRecordDao;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class OpRecordJdbcTemplateImplTest extends TestCase {
    @Autowired
    private OpRecordDao opRecordDao;

    @Test
    public void insertOpRecord(){
        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(1);
        opRecord.setOpmoney(5);
        opRecord.setOptype(OpType.DEPOSITE);
        this.opRecordDao.insertOpRecord(opRecord);
    }

    @Test
    public  void findOpRecord(){
        List<OpRecord> list = this.opRecordDao.findOpRecord(1);
        System.out.println(list);
    }


    @Test
    public  void testFindOpRecord(){
        List<OpRecord> list = this.opRecordDao.findOpRecord(2,"DEPOSITE");
        System.out.println(list);
    }

    @Test
    public  void testFindOpRecord1(){

    }

}
