@Entity
public class EventMergeRecord {
    @Id @GeneratedValue
    private Long id;
    private String sourceEventIds;
    private String mergedTitle;
    private LocalDate mergedStartDate;
    private LocalDate mergedEndDate;
    private String mergeReason;
    private LocalDateTime createdAt;

    public EventMergeRecord() {}

    public EventMergeRecord(Long id, String ids, String title, LocalDate s, LocalDate e, String reason, LocalDateTime c) {
        this.id = id; this.sourceEventIds = ids; this.mergedTitle = title;
        this.mergedStartDate = s; this.mergedEndDate = e;
        this.mergeReason = reason; this.createdAt = c;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public String getSourceEventIds() { return sourceEventIds; }
}
