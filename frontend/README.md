# Frontend — Shop (Vite + React)

This is a minimal scaffold for the online shop frontend.

Quick start:

- Using npm (PowerShell / cmd):

```powershell
npm install
npm run dev
```

- Using yarn (if you prefer yarn):

```powershell
yarn install
yarn dev
```

Then open http://localhost:5173

What I added:
- Vite + React scaffold (`package.json`, `vite.config.js`)
- Example login page at `src/pages/Login.jsx` and form component `src/components/LoginForm.jsx`
- Global styles in `src/styles/global.css`

Tailwind
 - This project includes Tailwind. After `npm install` a `postinstall` script will generate `src/styles/generated.css` from `src/styles/tailwind.css`.
 - If you want to regenerate manually:

If the automatic postinstall generation doesn't run (or tailwind binary isn't found), run the fallback manually:

```powershell
npx tailwindcss -i ./src/styles/tailwind.css -o ./src/styles/generated.css --minify
```

Or with yarn (preferred when using yarn):

```powershell
yarn run build:css
```

Next steps:
- Wire the form to a real auth API
- Add routing and protected routes
- Replace placeholder assets and copy

Notes for Windows PowerShell users:

- If you see an error like `npm.ps1 cannot be loaded because running scripts is disabled on this system`, either run the commands in Command Prompt (`cmd.exe`) or temporarily relax the execution policy for the current PowerShell session:

```powershell
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
npm install
```

Running in `cmd.exe` avoids the PowerShell script execution policy altogether:

```cmd
npm install
npm run dev
```
