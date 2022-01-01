package com.dasd412.remake.api.service.security;

import com.dasd412.remake.api.controller.exception.DuplicateEmailException;
import com.dasd412.remake.api.controller.exception.DuplicateUserNameException;
import com.dasd412.remake.api.domain.diary.EntityId;
import com.dasd412.remake.api.domain.diary.writer.Role;
import com.dasd412.remake.api.domain.diary.writer.Writer;
import com.dasd412.remake.api.domain.diary.writer.WriterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WriterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WriterRepository writerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WriterService(WriterRepository writerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.writerRepository = writerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //작성자 id 생성 메서드 (트랜잭션 필수)
    private EntityId<Writer, Long> getNextIdOfWriter() {
        Long count = writerRepository.findCountOfId();
        Long writerId;
        if (count == 0) {
            writerId = 0L;
        } else {
            writerId = writerRepository.findMaxOfId();
        }
        return EntityId.of(Writer.class, writerId + 1);
    }

    //비밀 번호 암호화
    private String encodePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Transactional
    public void saveWriterWithSecurity(String name, String email, String password, Role role) {
        logger.info("join writer with security");

        if (writerRepository.existsName(name) == Boolean.TRUE) {
            throw new DuplicateUserNameException("이미 존재하는 회원 이름입니다.");
        }

        if (writerRepository.existsEmail(email) == Boolean.TRUE) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }

        //비밀 번호 암호화
        String encodedPassword = encodePassword(password);

        //실제 회원 가입
        Writer writer = new Writer(getNextIdOfWriter(), name, email, encodedPassword, role);
        writerRepository.save(writer);
    }
}