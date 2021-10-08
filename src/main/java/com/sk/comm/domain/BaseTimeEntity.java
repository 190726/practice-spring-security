package com.sk.comm.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass //JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드도 컬럼으로 인식하도록 함.
@EntityListeners(AuditingEntityListener.class) //Auditing(Spring Data JPA에서 자동으로 값을 넣어줌) 기능 포함
public abstract class BaseTimeEntity {
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
