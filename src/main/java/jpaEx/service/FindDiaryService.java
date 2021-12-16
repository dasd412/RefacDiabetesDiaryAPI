package jpaEx.service;

import jpaEx.domain.diary.EntityId;
import jpaEx.domain.diary.diabetesDiary.DiabetesDiary;
import jpaEx.domain.diary.diabetesDiary.DiaryRepository;
import jpaEx.domain.diary.diet.Diet;
import jpaEx.domain.diary.diet.DietRepository;
import jpaEx.domain.diary.writer.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;


@Service
public class FindDiaryService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DiaryRepository diaryRepository;
    private final DietRepository dietRepository;

    public FindDiaryService(DiaryRepository diaryRepository, DietRepository dietRepository) {
        this.diaryRepository = diaryRepository;
        this.dietRepository = dietRepository;
    }

    /*
    조회용으로만 트랜잭션할 경우 readonly = true 로 하면 조회 성능 향상 가능
     */

    /*
    일지 조회 메서드들
     */
    @Transactional(readOnly = true)
    public Optional<Writer>getWriterOfDiary(EntityId<DiabetesDiary,Long>diaryEntityId){
        logger.info("getWriterOfDiary");
        return diaryRepository.findWriterOfDiary(diaryEntityId.getId());
    }

    @Transactional(readOnly = true)
    public List<DiabetesDiary> getDiabetesDiariesOfWriter(EntityId<Writer,Long>writerEntityId){
        logger.info("getDiabetesDiariesOfWriter");
        return diaryRepository.findDiabetesDiariesOfWriter(writerEntityId.getId());
    }

    @Transactional(readOnly = true)
    public Optional<DiabetesDiary> getDiabetesDiaryOfWriter(EntityId<Writer,Long>writerEntityId,EntityId<DiabetesDiary,Long>diabetesDiaryEntityId){
        logger.info("getDiabetesDiaryOfWriter");
        return diaryRepository.findDiabetesDiaryOfWriter(writerEntityId.getId(),diabetesDiaryEntityId.getId());
    }

    @Transactional(readOnly = true)
    public List<DiabetesDiary> getDiaryBetweenTime(LocalDateTime startDate, LocalDateTime endDate,EntityId<Writer,Long>writerEntityId){
        logger.info("getDiaryBetweenTime");
        checkArgument(startDate.isBefore(endDate),"startDate must be before than endDate");
        return diaryRepository.findDiaryBetweenTime(startDate,endDate,writerEntityId.getId());
    }

    @Transactional(readOnly = true)
    public List<DiabetesDiary> getFpgHigherOrEqual(int fastingPlasmaGlucose,EntityId<Writer,Long>writerEntityId){
        logger.info("getFpgHigherOrEqual");
        checkArgument(fastingPlasmaGlucose>0,"fpg must be positive");
        return diaryRepository.findFpgHigherOrEqual(fastingPlasmaGlucose,writerEntityId.getId());
    }

    @Transactional(readOnly = true)
    public List<DiabetesDiary> getFpgLowerOrEqual(int fastingPlasmaGlucose,EntityId<Writer,Long>writerEntityId){
        logger.info("getFpgLowerOrEqual");
        checkArgument(fastingPlasmaGlucose>0,"fpg must be positive");
        return diaryRepository.findFpgLowerOrEqual(fastingPlasmaGlucose,writerEntityId.getId());
    }

    /*
    식단 조회 메서드들
     */
    @Transactional(readOnly = true)
    public  List<Diet>getDietsOfDiary(EntityId<Writer,Long>writerEntityId,EntityId<DiabetesDiary,Long>diabetesDiaryEntityId){
        logger.info("getDietsOfDiary");
        return dietRepository.findDietsInDiary(writerEntityId.getId(),diabetesDiaryEntityId.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Diet>getOneDietOfDiary(EntityId<Writer,Long>writerEntityId,EntityId<DiabetesDiary,Long>diabetesDiaryEntityId,EntityId<Diet,Long>dietEntityId){
        logger.info("get only one diet in diary");
        return dietRepository.findDietInDiary(writerEntityId.getId(),diabetesDiaryEntityId.getId(),dietEntityId.getId());
    }

}
