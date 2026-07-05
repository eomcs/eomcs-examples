import js from "@eslint/js";
import globals from "globals";
import tseslint from "typescript-eslint";
import { defineConfig } from "eslint/config";

export default defineConfig([
  {
    files: ["ch*/src/**/*.ts"],
    plugins: { js },
    extends: ["js/recommended"],
    languageOptions: {
      globals: globals.node,
      parserOptions: {
        project: ["./ch*/tsconfig.json"]
      }
    }
  },
  ...tseslint.configs.recommended
]);
