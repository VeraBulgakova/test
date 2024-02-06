package com.vera.rnrc.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "response")
public class ResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "partner_id")
    private String partnerId;
    @Column(name = "check_object")
    private String checkObject;
    @Column(name = "linkedStructure_order")
    private String linkedStructureOrder;
    @Column(name = "participant_order")
    private String participantOrder;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "check_result")
    private String checkResult;
    @Column(name = "list_id")
    private String listId;
}
