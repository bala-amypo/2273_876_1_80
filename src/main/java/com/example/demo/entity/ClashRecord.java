@Entity
public class ClashRecord {
    @Id @GeneratedValue
    private Long id;
    private Long eventAId;
    private Long eventBId;
    private String clashType;
    private String severity;
    private String details;
    private LocalDateTime detectedAt;
    private Boolean resolved;

    public ClashRecord() {}

    public ClashRecord(Long id, Long a, Long b, String type, String sev, String det, LocalDateTime d, Boolean r) {
        this.id = id; this.eventAId = a; this.eventBId = b;
        this.clashType = type; this.severity = sev; this.details = det;
        this.detectedAt = d; this.resolved = r;
    }

    @PrePersist
    public void prePersist() {
        if (detectedAt == null) detectedAt = LocalDateTime.now();
        if (resolved == null) resolved = false;
    }

    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean r) { this.resolved = r; }
}
