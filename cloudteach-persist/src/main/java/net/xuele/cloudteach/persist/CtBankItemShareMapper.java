package net.xuele.cloudteach.persist;

import net.xuele.cloudteach.domain.CtBankItemShare;
import net.xuele.cloudteach.view.BankItemShareView;
import net.xuele.common.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CtBankItemShareMapper {

    int insert(CtBankItemShare record);

    CtBankItemShare selectByPrimaryKey(String shareId);

    int updateByPrimaryKey(CtBankItemShare record);

    /**
     * 分页查询
     * @param pageSize
     * @param page
     * @param record
     * @param seltype
     * @return
     */
    List<BankItemShareView> selectByPage(@Param(value = "pageSize") int pageSize,@Param("page") Page page,@Param("record") CtBankItemShare record, @Param("seltype") int seltype);

    /**
     * 查询总记录数
     * @return
     */
    int selectCount(@Param("record") CtBankItemShare record,@Param("seltype") int seltype);
    /**
     * 获取题目详情
     * @param shareId
     * @return
     */
    BankItemShareView showDetail(String shareId);

    /**
     * 取消分享
     * @param shareId
     * @return
     */
    int unShareBankItem(String shareId);

    /**
     * 分享审核成功
     * @param shareId
     * @return
     */
    int shareBankItemSuc(String shareId);

    /**
     * 分享审核失败
     * @param shareId
     * @return
     */
    int shareBankItemFail(String shareId);

    /**
     * 根据原题id查询分享的题目
     * @param itemId
     * @return
     */
    CtBankItemShare selectSharedRecordByItemid(String itemId);
}