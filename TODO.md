# TODO — Confluence Düzeltmeleri

Aşağıdaki sorunlar tespit edildi. Sırasıyla çözülecek.

---

## Sorun 1: Numara Çakışması (4 çift aynı numara)

Aynı numarayı paylaşan farklı konulardaki dosyalar:

| Numara | Dosya A | Dosya B | Durum |
|:------:|---------|---------|-------|
| 07 | `07-backlog-detailed.md` (stories+tasks) | `07-references.md` (referanslar) | Farklı konular |
| 08 | `08-backlog-summary.md` (Jira-ready) | `08-decisions-adr.md` (5 ADR detay) | Farklı konular |
| 10 | `10-decisions-adr.md` (ADR özet) | `10-glossary.md` (terimler) | Farklı konular |
| 11 | `11-project-plan-and-governance.md` (proje planı) | `11-security-plan.md` (güvenlik) | Farklı konular |

**Çözüm:** Tüm dosyaları tutarlı sırayla yeniden numaralandır. Hiçbir dosya silinmeyecek — sadece rename.

---

## Sorun 2: İçerik Tekrarı (Aynı konuyu kapsayan birden fazla dosya)

| Konu | Dosya 1 | Dosya 2 | Durum |
|------|---------|---------|-------|
| ADR | `08-decisions-adr.md` (375 satır, detaylı prose) | `10-decisions-adr.md` (417 satır, özet prose) | İkisi de 5 ADR kapsıyor |
| Glossary | `10-glossary.md` (79 satır) | `14-glossary.md` (71 satır) | İkisi de aynı terimler |
| Security | `11-security-plan.md` (165 satır) | `13-security-plan.md` (166 satır) | İkisi de aynı konu |
| Backlog | `07-backlog-detailed.md` (513 satır) | `08-backlog-summary.md` + `15-detailed-task-definitions.md` | 07 tamamen kapsanıyor |

**Çözüm:** Her konu için İKİ dosyadan en zengin parçaları TEK dosyaya birleştir. Diğer dosyayı silmek yerine, birleştirilmiş dosyaya yönlendirme notu koy.

---

## Sorun 3: Parent Overview Child Listesi Eksik

`00-parent-overview.md` child page tablosu sadece 16 sayfa listeliyor. Mevcut 5 dosya listede yok:
- `07-backlog-detailed.md`
- `08-decisions-adr.md`
- `10-glossary.md`
- `11-security-plan.md`
- `17-source-content-coverage.md`

**Çözüm:** Renumber sonrası parent'ı ve README'yi güncellenmiş child listesiyle eşleştir.

---

## Sorun 4: Standards Uyum Eksiklikleri

Standards.md gereksinimlerine göre kontrol:

| Gereksinim | Durum | Eksik dosyalar |
|------------|-------|----------------|
| Metadata (Owner/Created by, Status, Last updated) | ✅ Hepsinde var | — |
| Feedback mechanism | ✅ Hepsinde var | — |
| Labels bilgisi | ⚠️ Sadece 00, 15, README'de | 01–14, 16, 17 |
| Executive summary / Purpose en üstte | ✅ Hepsinde var | — |
| Self-contained (dış repo referansı yok) | ✅ Temiz | — |
| Tone ("Approach" not "Recommendation") | ✅ Uygun | — |
| Code blocks language specified | ⚠️ Kontrol gerekli | — |
| TBC items clearly marked | ✅ Doğru kullanılmış | — |

**Çözüm:** Her sayfaya labels satırı ekle (metadata tablosuna).

---

## Sorun 5: README ve Parent Child Listesi Tutarsızlık

README'deki "Page Structure" tablosu sadece Set B'yi (00–16) listeliyor. Set A dosyaları (07-backlog-detailed, 08-decisions-adr, 10-glossary, 11-security-plan) yok.

**Çözüm:** Renumber sonrası README'yi güncellenmiş listeyle eşleştir.

---

## Eylem Planı

### Adım 1: İçerik Birleştirme (tekrar eden dosyalar)

Her çift için en zengin içeriği TEK dosyada birleştir:

1. **ADR:** `08-decisions-adr` (375 satır detaylı prose) + `10-decisions-adr` (ADR Index, template) → TEK birleşik ADR dosyası
2. **Glossary:** `10-glossary` (terimler + detaylı environment) + `14-glossary` (ekstra terimler + GitLab bilgisi) → TEK birleşik glossary
3. **Security:** `11-security-plan` + `13-security-plan` → TEK birleşik security
4. **Backlog:** `07-backlog-detailed` tamamen `08-backlog-summary` + `15-detailed-task-definitions` tarafından kapsanıyor → 07'yi kaldır veya redirect koy

### Adım 2: Yeniden Numaralandırma

Birleştirme sonrası tutarlı 00–N sırasıyla yeniden numara ver. Boşluk bırakma.

Hedef yapı (tahmini):
```
00 - Parent Overview
01 - Proposal Matrix
02 - Phased Plan
03 - Risks and DACI
04 - Technical Details
05 - Pipeline and Drone
06 - Deployment and Release
07 - References
08 - Backlog Summary
09 - Detailed Task Definitions
10 - Architecture Decisions (ADR) ← birleşik
11 - Future Considerations
12 - Project Plan and Governance
13 - Working Agreements and Metrics
14 - Security Plan ← birleşik
15 - Glossary ← birleşik
16 - Code Examples and Templates
17 - Source Content Coverage
```

### Adım 3: Parent + README Güncelleme

- `00-parent-overview.md` child tablosunu yeni numaralarla eşleştir
- `confluence/README.md` page structure tablosunu eşleştir

### Adım 4: Labels Ekleme

Her sayfanın metadata tablosuna `Labels` satırı ekle:
```
| **Labels** | `proposal`, `ci-cd`, `pilot`, `cerberus-delivery` |
```

### Adım 5: Code Block Language Kontrolü

Tüm code block'larda dil belirtildiğinden emin ol (```java, ```dockerfile, ```yaml, ```bash, ```xml, ```text).

### Adım 6: Final Doğrulama

- [ ] Numara çakışması yok
- [ ] İçerik tekrarı yok
- [ ] Parent child listesi = gerçek dosyalar
- [ ] README page structure = gerçek dosyalar
- [ ] Her dosyada metadata + labels + feedback
- [ ] Dış repo referansı yok (self-contained)
- [ ] Tüm iç linkler çalışıyor

---

## Notlar

- Hiçbir dosya silinmeyecek — birleştirilen dosyalar rename veya redirect ile ele alınacak.
- Her adımdan sonra kontrol yapılacak.
- Commit ve push sadece kullanıcı onayı ile.
