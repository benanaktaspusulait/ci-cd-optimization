# TODO — Confluence Self-Contained Rewrite

Mevcut `confluence/` klasörünü sıfırdan yeniden yaz. Her sayfa:
- Tamamen self-contained (bağımsız okunabilir)
- Hiçbir yerde repo, GitLab, docs/, examples/, .md dosyası referansı YOK
- Tüm bilgi sayfanın kendisinde (kod örnekleri dahil)
- Kaynak dokümanlardan hiçbir şey eksik değil
- Confluence standartlarına göre extralar eklenmiş

---

## Kurallar

1. **Referans vermeme:** "see repo", "see docs/adr/", "examples/ klasöründe", "requires GitLab access" gibi ifadeler YOK
2. **Self-contained:** bir okuyucu sadece bu sayfaları görüyor, başka hiçbir şey yok
3. **Tam içerik:** kaynak dokümanlardan eksik bilgi yok — hatta fazlası var
4. **Kod örnekleri inline:** Java kodu, Dockerfile, docker-compose hepsi sayfa içinde
5. **İç linkler sadece confluence/ içi:** sayfalar birbirine referans verebilir (child ↔ parent)
6. **Tone:** standards.md'ye uygun — "Approach", TBC, cautious, collaborative

---

## Kaynak dosyalar (hepsinin içeriği confluence'a aktarılacak)

- README.md (epic, success targets, stories, approach, scope)
- docs/PROJECT-CONTEXT.md (background, current state, business impact, tech stack)
- docs/PIPELINE-CONTEXT.md (Drone, RepoSync, pipeline landscape)
- docs/SCOPE-AND-GUARDRAILS.md (scope, assumptions, open questions, deferred, first changes)
- PROJECT-PLAN.md (timeline, risk register, branching, test strategy)
- SECURITY.md (secrets, scanning, policy-as-code, supply chain)
- docs/glossary.md (tüm terimler)
- docs/stories/INDEX.md + STATUS-BOARD.md (task listesi)
- docs/stories/DEFINITION-OF-DONE.md + metrics-template.md
- docs/stories/tech-notes.md (base image, BuildKit, Testcontainers, security)
- docs/stories/FUTURE-CONSIDERATIONS.md (F1-F7, architecture decisions, technical opportunities, deployment/release)
- docs/adr/0001–0005 (5 ADR tam metin)
- docs/stories/story-1 through story-6 README.md (6 story goal/why/acceptance)
- docs/stories/story-*/task-*.md (23 task — why/goal/scope/acceptance criteria)
- examples/testcontainers/*.java (Cucumber+Testcontainers kodu)
- examples/docker/Dockerfile + .dockerignore + docker-compose.yml
- examples/ci/drone-considerations.md

---

## Hedef yapı (değişmiyor — sadece içerik genişliyor)

```
confluence/
├── README.md                         → Kullanım rehberi (Confluence'a nasıl yüklenir)
├── 00-parent-overview.md             → Executive summary (kısa, karar-dostu)
├── 01-proposal-matrix.md             → Tüm öneriler Value/Risk/Complexity/Effort/MoSCoW
├── 02-phased-plan.md                 → Phase 1–4 detaylı
├── 03-risks-and-daci.md              → Risk register + DACI areas
├── 04-technical-details.md           → Dockerfile, Testcontainers, BuildKit, Compose, Security — TÜM KOD ÖRNEKLERİ İÇİNDE
├── 05-pipeline-and-drone.md          → Drone/RepoSync/CI tam bağlam
├── 06-deployment-and-release.md      → Deploy pipeline tam bağlam
├── 07-backlog-detailed.md            → 6 story + 23 task TAM DETAY (why/goal/scope/acceptance)
├── 08-decisions-adr.md               → 5 ADR tam metin
├── 09-future-considerations.md       → F1-F7 + architecture + technical opportunities + release safety
├── 10-glossary.md                    → Tüm terimler
└── 11-security-plan.md               → SECURITY.md tam içerik
```

---

## Yapılacaklar

### A. Mevcut confluence/ dosyalarını sil ve sıfırdan yaz

- [ ] **A1.** Mevcut confluence/ içeriğini sil (README hariç — onu da yeniden yazacağız)

### B. 00-parent-overview.md (kısa — max 150 satır)

- [ ] **B1.** Metadata (Owner, Status, Created, Last updated)
- [ ] **B2.** Executive summary (5-6 cümle)
- [ ] **B3.** Context / Problem statement
- [ ] **B4.** Objectives
- [ ] **B5.** Scope (in/out)
- [ ] **B6.** Approach
- [ ] **B7.** Ownership boundaries (CST | ACP | DSA ETO) — kısa tablo
- [ ] **B8.** Success targets (CST-local + platform-dependent)
- [ ] **B9.** Story overview tablosu
- [ ] **B10.** Child page linkleri (confluence/ içi linkler — başka yere değil)
- [ ] **B11.** NO external references

### C. 01-proposal-matrix.md

- [ ] **C1.** Metadata
- [ ] **C2.** 10 proposal satırı (tam açıklamalı)
- [ ] **C3.** Quick win guidance
- [ ] **C4.** Self-contained — no links outside confluence/

### D. 02-phased-plan.md

- [ ] **D1.** Metadata
- [ ] **D2.** Phase 1–4 tam detay (objective, changes, outcome, success criteria, risks)
- [ ] **D3.** Timeline tablo (Week 1–4)
- [ ] **D4.** Milestones
- [ ] **D5.** Self-contained

### E. 03-risks-and-daci.md

- [ ] **E1.** Metadata
- [ ] **E2.** R1–R8 tam risk register (risk + impact + mitigation + owner)
- [ ] **E3.** DACI areas (D1–D5 tam)
- [ ] **E4.** Self-contained

### F. 04-technical-details.md (EN BÜYÜK — tüm kod örnekleri burada)

- [ ] **F1.** Metadata
- [ ] **F2.** Dockerfile: current state + proposed multi-stage (TAM Dockerfile inline)
- [ ] **F3.** .dockerignore (tam önerilen içerik inline)
- [ ] **F4.** docker-compose.yml (tüm servisler tam — Redis, Zookeeper, Kafka, SchemaRegistry, LocalStack, Jaeger, Kafdrop)
- [ ] **F5.** Testcontainers: RedisContainerConfig.java TAM KOD
- [ ] **F6.** Testcontainers: KafkaContainerConfig.java TAM KOD (Zookeeper + SchemaRegistry)
- [ ] **F7.** Testcontainers: CucumberSpringConfig.java TAM KOD
- [ ] **F8.** Testcontainers: TestcontainersBaseIT.java TAM KOD
- [ ] **F9.** Testcontainers: pom.xml dependency snippet (tam, hangi module'a ne eklenir)
- [ ] **F10.** Testcontainers: Maven profile (testcontainers profili)
- [ ] **F11.** BuildKit: local cache mounts açıklama + komut
- [ ] **F12.** BuildKit: remote cache pattern (post-pilot)
- [ ] **F13.** BuildKit: measure-baseline.sh konsept (ne yapar, nasıl çalışır)
- [ ] **F14.** Base image strategy (4 katman açıklaması)
- [ ] **F15.** Testcontainers reuse policy (local vs CI)
- [ ] **F16.** Self-contained — no links outside confluence/

### G. 05-pipeline-and-drone.md

- [ ] **G1.** Metadata
- [ ] **G2.** Pipeline landscape diyagramı (CI vs Deploy)
- [ ] **G3.** Drone/RepoSync constraint (tam açıklama)
- [ ] **G4.** CI pipeline steps listesi (13 step — tam)
- [ ] **G5.** Local vs RepoSync boundary tablosu
- [ ] **G6.** Testcontainers CI feasibility (bilinen + sorular + olası sonuçlar)
- [ ] **G7.** BuildKit CI feasibility
- [ ] **G8.** MR pipeline behaviour
- [ ] **G9.** Drone considerations (önceden examples/ci/drone-considerations.md'de olan TÜM bilgi)
- [ ] **G10.** Self-contained

### H. 06-deployment-and-release.md

- [ ] **H1.** Metadata + "outside pilot scope" disclaimer
- [ ] **H2.** Deploy pipeline (MMA Helm repo) tam açıklama
- [ ] **H3.** Release flow diyagramı
- [ ] **H4.** Environments tablosu
- [ ] **H5.** Rollback durumu
- [ ] **H6.** Feature flags
- [ ] **H7.** Validation stages
- [ ] **H8.** Release automation (Gareth)
- [ ] **H9.** Self-contained

### I. 07-backlog-detailed.md (EN BÜYÜK 2 — tüm task detayları)

- [ ] **I1.** Metadata + candidate disclaimer
- [ ] **I2.** Story overview tablosu (6 story)
- [ ] **I3.** Story 1 — 5 task TAM detay (her biri: ID, Type, Estimate, Priority, Sprint, Why, Goal, Scope, Acceptance criteria)
- [ ] **I4.** Story 2 — 4 task TAM detay
- [ ] **I5.** Story 3 — 4 task TAM detay
- [ ] **I6.** Story 4 — 4 task TAM detay
- [ ] **I7.** Story 5 — 3 task TAM detay
- [ ] **I8.** Story 6 — 3 task TAM detay
- [ ] **I9.** Ticket creation order
- [ ] **I10.** Estimates explanation
- [ ] **I11.** Definition of Done (tam — every task, measurement tasks, code tasks)
- [ ] **I12.** Metrics template (tam tablo + source mapping)
- [ ] **I13.** Self-contained

### J. 08-decisions-adr.md

- [ ] **J1.** Metadata
- [ ] **J2.** ADR-0001 TAM METİN (Context, Decision, Consequences, Alternatives tablosu)
- [ ] **J3.** ADR-0002 TAM METİN
- [ ] **J4.** ADR-0003 TAM METİN
- [ ] **J5.** ADR-0004 TAM METİN (Dockerfile pattern inline)
- [ ] **J6.** ADR-0005 TAM METİN
- [ ] **J7.** Self-contained

### K. 09-future-considerations.md

- [ ] **K1.** Metadata + ACP/DSA ETO disclaimer
- [ ] **K2.** F1–F7 production readiness tablosu (tam)
- [ ] **K3.** Priority order
- [ ] **K4.** Architecture decisions: base image + remote cache (tam)
- [ ] **K5.** Technical opportunities: selective test, Drone templates, contract testing, ephemeral envs, dep proxy, release automation (tam)
- [ ] **K6.** Deployment & release safety (KT findings tam)
- [ ] **K7.** Self-contained

### L. 10-glossary.md

- [ ] **L1.** Metadata
- [ ] **L2.** Tüm terimler (FDP, CST, ETO, ACP, Drone, RepoSync, Starlark, DIND, MR, MMA Helm repo, Helm, Artifactory, SIT, bVal, QAT, feature flag, tools pod, PNR room, BuildKit, ADR, DoD, MoSCoW, SBOM, Testcontainers, Remote cache)
- [ ] **L3.** Environment clarification tablosu
- [ ] **L4.** Self-contained

### M. 11-security-plan.md

- [ ] **M1.** Metadata
- [ ] **M2.** Secret management (tam tablo + BuildKit pattern inline)
- [ ] **M3.** Scanning policy (tam tablo + severity policy)
- [ ] **M4.** Policy as code (tam tablo + enforcement approach)
- [ ] **M5.** Supply-chain hardening
- [ ] **M6.** Responsibilities tablo
- [ ] **M7.** Self-contained

### N. confluence/README.md

- [ ] **N1.** Sadece Confluence'a yükleme talimatları
- [ ] **N2.** Page structure listesi
- [ ] **N3.** Labels önerisi
- [ ] **N4.** Self-contained (repo'ya link yok)

### O. Commit & push

- [ ] **O1.** TODO.md sil
- [ ] **O2.** standards.md kalsın (bu kendi referansımız)
- [ ] **O3.** Commit + push
- [ ] **O4.** Satır sayısı kontrolü (hedef: ≥3000 satır — kaynak 6058'in anlamlı kısmı)

---

## Sıralama

```
A → B → C → D → E → F → G → H → I → J → K → L → M → N → O
```

F (technical) ve I (backlog) en büyük dosyalar — toplam ~1500 satır bekleniyor.
