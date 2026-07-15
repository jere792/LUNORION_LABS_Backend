# Sprint 01 — Dominid (Gestor Fullstack)

## Resumen

Responsable de la infraestructura del proyecto, autenticación JWT + PBAC, gestión de tenants, usuarios y permisos. También coordina code review de todo el equipo.

---

## Tareas

| # | Tarea | Estado | Endpoint / Detalle |
|:---:|:---|:---:|:---|
| 1 | Crear repositorio con ramas `deployed`, `test`, `programacion` | ✅ | GitHub |
| 2 | Configurar protect rules (PRs requeridos, approvals) | ✅ | GitHub |
| 3 | Configurar CI/CD (build, test, lint) | ✅ | GitHub Actions |
| 4 | Coordinar code review de todo el equipo | ✅ | Gestión |
| 5 | Documentar estándares del proyecto (convenciones, commits) | ✅ | Docs |
| 6 | Auth: módulo de login + JWT + refresh token | ✅ | `POST /api/auth/login`, `POST /api/auth/refresh` |
| 7 | Auth: middleware/filter de permisos PBAC | ✅ | Spring Security |
| 8 | CRUD Tenants | ✅ | `GET/POST/PUT/DELETE /api/tenants` |
| 9 | CRUD Usuarios + asignar permisos | ✅ | `POST/GET /api/usuarios`, `PUT /api/usuarios/{id}/permisos`, `GET /api/usuarios/tenant/{tenantId}`, `POST /api/usuarios/{id}/desactivar` |
| 10 | Listar catálogo de permisos | ✅ | `GET /api/usuarios/permisos?tenantId=` |
| 11 | Permisos: crear nuevo permiso | ⬜ | `POST /api/permisos` (no existe en código) |
| 12 | Permisos: desactivar | ⬜ | `POST /api/permisos/{id}/desactivar` (no existe en código) |

---

## Auditoría de Código

| Verificado | Archivo |
|:---|:---|
| AuthController | `core/auth/infrastructure/adapters/in/http/AuthController.java` — 2 endpoints |
| TenantController | `core/tenant/infrastructure/adapters/in/http/TenantController.java` — 5 endpoints |
| UsuarioController | `core/usuario/infrastructure/adapters/in/http/UsuarioController.java` — 6 endpoints (incluye GET permisos) |
| PermisoJpaRepository | `core/usuario/.../PermisoJpaRepository.java` — existe pero sin endpoints POST |

---

## Estado Global

**Progreso:** 10/12 (83%)
