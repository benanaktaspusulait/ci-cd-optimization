# TODO — Drone/RepoSync Discovery Güncellemesi

Samples dosyasındaki `.drone.star` analizi sonrası yapılması gereken tüm değişiklikler.
Her madde tamamlandığında `[x]` olarak işaretlenecek.

---

## Bağlam

- CI sistemi: **Drone** (Kubernetes runner, DIND service)
- Pipeline config: **`.drone.star`** (Starlark) — **RepoSync ile merkezi yönetimli**
- Kaynak kod hosting: `gitlab.digital.homeoffice.gov.uk`
- Lokal değişiklikler overwrite ediliyor → pipeline değişiklikleri CST-local değil

---

## Yapılacaklar

### A. README.md güncellemeleri

- [x] **A1.** Technology stack tablosunda "GitLab CI" → "Drone CI (Kubernetes runner, `.drone.star` via RepoSync)" olarak güncelle
- [x] **A2.** Yeni bölüm ekle: "Drone / RepoSync Pipeline Considerations" — core constraint olarak açıkla
- [x] **A3.** "Immediate pilot scope" bölümünü ikiye ayır: "CST-local (repo içi)" vs "Central/platform (RepoSync/ETO)"
- [x] **A4.** "Open questions" bölümüne Drone-specific sorular ekle
- [x] **A5.** "Do not do yet" bölümüne RepoSync uyarısı ekle
- [x] **A6.** Epic title/description'da CI sistemi referansını düzelt

### B. Yeni Story 0 — Drone/RepoSync Pipeline Assessment

- [x] **B1.** `docs/stories/story-0-pipeline-assessment/README.md` oluştur
- [x] **B2.** Task dosyaları oluştur
- [x] **B3.** INDEX.md'ye Story 0'ı ekle
- [x] **B4.** README status board'a Story 0 task'larını ekle
- [x] **B5.** Story dependency akışını güncelle (Story 0 → Story 1 → ...)

### C. Mevcut story'lerde Drone-aware düzeltmeler

- [x] **C1.** Story 3 (Testcontainers): README ve task'lara "Drone DIND + Ryuk disabled" constraint'i ekle
- [x] **C2.** Story 2 (Build): Task'lara "BuildKit feasibility in Drone" bağımlılığı ekle
- [x] **C3.** Story 5 (Findings): "Local vs RepoSync" ownership ayrımını scope'a ekle

### D. Dosya temizliği — `.gitlab-ci.yml` → Drone

- [x] **D1.** Kök `.gitlab-ci.yml`'i sil veya `examples/ci/` altına taşı (template olarak kal, kök kaldır)
- [x] **D2.** `examples/ci/gitlab-ci-integration-test.yml` → "illustrative only, actual CI is Drone" notu ekle
- [x] **D3.** `examples/ci/drone-considerations.md` oluştur — Drone pipeline'da Testcontainers/BuildKit nasıl çalışabilir

### E. ADR güncellemeleri

- [x] **E1.** ADR-0004 (BuildKit): "requires RepoSync/.drone.star change" notu ekle
- [x] **E2.** ADR-0005 (CI runner mode): Drone Kubernetes runner + DIND'e göre yeniden yaz
- [x] **E3.** ADR README index'ini güncelle (ADR-0005 title değişirse)

### F. Diğer dosyalar

- [x] **F1.** `docs/stories/tech-notes.md`: BuildKit remote cache bölümüne Drone constraint ekle
- [x] **F2.** `PROJECT-PLAN.md`: Risk register'a "R7 — RepoSync overwrites local pipeline changes" ekle
- [x] **F3.** `CONTRIBUTING.md`: Structure bölümünde `.gitlab-ci.yml` referansını düzelt
- [x] **F4.** `SECURITY.md`: CI secret store referansını Drone'a güncelle (Drone secrets)
- [x] **F5.** `docs/glossary.md`: "Drone", "RepoSync", "Starlark", "DIND" tanımlarını ekle/güncelle
- [x] **F6.** `FUTURE-CONSIDERATIONS.md`: "Reusable Drone pipeline templates" olarak güncelle

### G. Commit & push

- [ ] **G1.** Tüm değişiklikleri tek commit ile push et

---

## Sıralama

```
A (README) → B (Story 0) → C (mevcut story düzeltmeleri) → D (dosya temizliği) → E (ADR) → F (diğer) → G (push)
```

Her adımda onayını alacağım.
