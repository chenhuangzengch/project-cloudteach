package net.xuele.cloudteach.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Magic2WorkListDTO
 *
 * @author panglx
 * @date on 2015/10/22 0022.
 */
public class Magic2WorkQuestInfoDTO implements Serializable {

    private static final long serialVersionUID = -3114199290477071069L;

    private QQuestDTO qQuestDTO;

    private List<QAnswerDTO> qAnswerDTOs;

    public QQuestDTO getqQuestDTO() {
        return qQuestDTO;
    }

    public void setqQuestDTO(QQuestDTO qQuestDTO) {
        this.qQuestDTO = qQuestDTO;
    }

    public List<QAnswerDTO> getqAnswerDTOs() {
        return qAnswerDTOs;
    }

    public void setqAnswerDTOs(List<QAnswerDTO> qAnswerDTOs) {
        this.qAnswerDTOs = qAnswerDTOs;
    }

    @Override
    public String toString() {
        return "Magic2WorkQuestInfoDTO{" +
                "qAnswerDTOs=" + qAnswerDTOs +
                ", qQuestDTO=" + qQuestDTO +
                '}';
    }
}
