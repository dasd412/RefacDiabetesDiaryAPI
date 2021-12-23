package com.dasd412.remake.api.controller.diary;

import com.dasd412.remake.api.controller.ApiResult;
import com.dasd412.remake.api.domain.diary.EntityId;
import com.dasd412.remake.api.service.SaveDiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dasd412.remake.api.controller.diary.diabetesdiary.DiabetesDiaryRequestDTO;
import com.dasd412.remake.api.controller.diary.diabetesdiary.DiabetesDiaryResponseDTO;
import com.dasd412.remake.api.controller.diary.diet.DietRequestDTO;
import com.dasd412.remake.api.controller.diary.diet.DietResponseDTO;
import com.dasd412.remake.api.controller.diary.food.FoodRequestDTO;
import com.dasd412.remake.api.controller.diary.food.FoodResponseDTO;
import com.dasd412.remake.api.controller.diary.writer.WriterJoinRequestDTO;
import com.dasd412.remake.api.controller.diary.writer.WriterJoinResponseDTO;

import com.dasd412.remake.api.domain.diary.diabetesDiary.DiabetesDiary;
import com.dasd412.remake.api.domain.diary.diet.Diet;
import com.dasd412.remake.api.domain.diary.writer.Writer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class DiarySaveRestController {
    /*
    컨트롤러 계층에는 엔티티가 사용되선 안되며 DTO 로 감싸줘야 한다.
     */

    private final SaveDiaryService saveDiaryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DiarySaveRestController(SaveDiaryService saveDiaryService) {
        this.saveDiaryService = saveDiaryService;
    }

    //todo writer 는 스프링 시큐리티 적용 후 빼낼 예정
    @PostMapping("/api/diary/writer")
    public ApiResult<WriterJoinResponseDTO> joinWriter(@RequestBody WriterJoinRequestDTO dto) {
        logger.info("join writer : " + dto.toString());
        return ApiResult.OK(new WriterJoinResponseDTO(saveDiaryService.saveWriter(dto.getName(), dto.getEmail(), dto.getRole())));
    }

    @PostMapping("api/diary/diabetes_diary")
    public ApiResult<DiabetesDiaryResponseDTO> postDiary(@RequestBody DiabetesDiaryRequestDTO dto) {
        logger.info("post diary : " + dto.toString());
        //컨트롤러 테스트 시, LocalDateTime->JSON 으로 직렬화를 못한다. 그걸 해결하기 위한 코드 두 줄.
        String date = dto.getYear() + "-" + dto.getMonth() + "-" + dto.getDay() + " " + dto.getHour() + ":" + dto.getMinute() + ":" + dto.getSecond();
        LocalDateTime writtenTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("writtenTime : " + writtenTime);
        return ApiResult.OK(new DiabetesDiaryResponseDTO(saveDiaryService.saveDiaryOfWriterById(EntityId.of(Writer.class, dto.getWriterId()), dto.getFastingPlasmaGlucose(), dto.getRemark(), writtenTime)));
    }

    @PostMapping("api/diary/diet")
    public ApiResult<DietResponseDTO> postDiet(@RequestBody DietRequestDTO dto) {
        logger.info("post Diet : " + dto.toString());
        return ApiResult.OK(new DietResponseDTO(saveDiaryService.saveDietOfWriterById(EntityId.of(Writer.class, dto.getWriterId()), EntityId.of(DiabetesDiary.class, dto.getDiaryId()), dto.getEatTime(), dto.getBloodSugar())));
    }

    @PostMapping("api/diary/food")
    public ApiResult<FoodResponseDTO> postFood(@RequestBody FoodRequestDTO dto) {
        logger.info("post Food : " + dto.toString());
        return ApiResult.OK(new FoodResponseDTO(saveDiaryService.saveFoodOfWriterById(EntityId.of(Writer.class, dto.getWriterId()), EntityId.of(DiabetesDiary.class, dto.getDiaryId()), EntityId.of(Diet.class, dto.getDietId()), dto.getFoodName())));
    }

}