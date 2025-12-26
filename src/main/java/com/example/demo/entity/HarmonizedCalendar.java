@Entity
public class HarmonizedCalendar {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String generatedBy;
    private LocalDateTime generatedAt;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Column(columnDefinition = "TEXT")
    private String eventsJson;

    public HarmonizedCalendar() {}

    public HarmonizedCalendar(Long id, String t, String g, LocalDateTime ga,
                              LocalDate f, LocalDate to, String json) {
        this.id = id; this.title = t; this.generatedBy = g;
        this.generatedAt = ga; this.effectiveFrom = f; this.effectiveTo = to;
        this.eventsJson = json;
    }

    @PrePersist
    public void prePersist() {
        if (generatedAt == null) generatedAt = LocalDateTime.now();
    }

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public String getGeneratedBy() { return generatedBy; }
}
