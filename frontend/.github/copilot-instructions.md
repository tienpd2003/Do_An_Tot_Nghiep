
1) First steps (always)
 - Open `package.json` and list the `scripts` section. Use the scripts rather than guessing commands.
 - Check `README.md` (repo root) for project-specific dev notes.
 - Inspect `src/` (or `app/`, `pages/`, `components/`) to learn structure and common patterns.

2) Architecture & boundaries (what to look for)
 - Identify the UI framework: look for `react`, `next`, `vue`, or `svelte` in `package.json` dependencies.
 - Locate the app entry: `src/index.tsx`, `src/main.tsx`, `pages/_app.tsx`, or `app/layout.tsx`.
 - Server/API integration: search for `fetch`, `axios`, `lib/api`, or `services/` to find where remote calls live. Check for environment keys like `VITE_API_URL` or `NEXT_PUBLIC_API_URL` in `.env` files.

3) Common repo-specific conventions (replace examples with discovered patterns)
 - Components: `src/components/*` (presentational components only). Prefer `.tsx` for React/TS projects.
 - State/hooks: `src/hooks/*` or `src/state/*` for custom hooks and global store.
 - API clients: `src/lib/api.ts` or `src/services/*` exporting a single configured client (axios/fetch wrapper). Modify there for cross-cutting concerns (auth header, interceptors).
 - Types: `src/types` or `types/` for shared TypeScript interfaces.

4) Build, run, and test workflows (how to verify changes)
 - Typical commands (confirm in `package.json`):
   - Install: `npm install` or `pnpm install`
   - Dev: `npm run dev` / `npm run start:dev`
   - Build: `npm run build`
   - Test: `npm run test` (or `vitest` / `jest` scripts) — run with `--watch` if inspecting interactively.
 - When adding scripts, update `README.md` and `package.json`'s scripts key.

5) Formatting, linting, and CI
 - Look for `eslint`, `prettier`, `husky`, and `lint-staged` in `package.json`.
 - Respect existing ESLint rules and TypeScript settings. Do not reformat files globally unless requested.

6) Integration and infra hooks
 - Environment: search for `.env`, `.env.local`, or references to `process.env` keys. Use `NEXT_PUBLIC_*` or `VITE_` prefixes as appropriate for public variables.
 - Backend: identify base URL and auth token location (cookie vs localStorage). Update API client centrally rather than in-call sites.

7) Making changes (agent etiquette & expectations)
 - Small, focused PRs. Change one concern at a time (bugfix, refactor, feature).
 - Update or add tests (unit/integ) adjacent to the changed code. If no tests exist, add a simple smoke test demonstrating behavior.
 - Keep modifications minimal: avoid touching unrelated files or broad stylistic changes.

8) When the repository is empty or files are missing
 - Stop and ask the human: request the project files or run `git status` / `dir` so the agent can inspect.
 - Provide a clear required-file checklist: `package.json`, `README.md`, `src/`, and any build configs (`vite.config.ts`, `next.config.js`).

9) Checklist for PRs by AI
 - [ ] Confirm dev scripts in `package.json` and update `README.md` if new commands are added
 - [ ] Add or update tests for new behavior
 - [ ] Keep commits atomic and include concise messages
 - [ ] Run lint and fix only the files changed in the PR unless asked otherwise

If any section below should include concrete examples from the codebase, open the files and replace the placeholders. Ask the repo owner for local environment variables or secrets if required for running the dev server.

---
Generated: 2025-10-12 — Template created because no source files were discoverable by the agent. Please update with repo-specific details.
