package jpaEx.domain.writer;

import jpaEx.domain.BaseTimeEntity;
import jpaEx.domain.diary.DiabetesDiary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name="Writer")
public class Writer extends BaseTimeEntity {
    @Id
    @Column(name="writer_id",columnDefinition = "bigint default 0")
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    //연관된 엔티티의 컬렉션을 로딩하는 것은 비용이 너무 많이 드므로 지연 로딩.
    //orphanRemoval=true 로 지정하면, 부모 엔티티 컬렉션에서 자식 엔티티를 삭제할 때 참조가 끊어지면서 db 에도 삭제된다.
    //cascade = CascadeType.ALL 을 적용하면 이 엔티티에 적용된 작업이 연관된 다른 엔티티들에도 모두 적용이 전파된다.
    @OneToMany(mappedBy = "writer",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<DiabetesDiary>diaries=new ArrayList<>();

    public Writer(){}

    public Writer(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void modifyName(String name) {
        checkArgument(name.length()>0 && name.length()<=50, "name should be between 1 and 50");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void modifyEmail(String email) {
        //CHECK Email
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void modifyRole(Role role) {
        this.role = role;
    }

    public List<DiabetesDiary> getDiaries() {
        return diaries;
    }

    public void addDiary(DiabetesDiary diary){
        this.diaries.add(diary);
        //무한 루프 방지
        if(diary.getWriter()!=this){
            diary.modifyWriter(this);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id",id)
                .append("name",name)
                .append("email",email)
                .append("role",role)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Writer target = (Writer) obj;
        return Objects.equals(this.id,target.id);
    }
}
