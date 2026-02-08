#!/usr/bin/env bash
set -euo pipefail

max_size_bytes=$((MAX_SIZE_MB * 1024 * 1024))

# Go over all files in this PR
large_files=""
while IFS= read -r file; do
  if [ -f "$file" ]; then
    size=$(stat --format=%s "$file")
    if [ "$size" -gt "$max_size_bytes" ]; then
      # Use awk instead of bash to do floating point math
      # (5.3 MB instead of 5 MB)
      size_mb=$(awk "BEGIN {printf \"%.1f\", $size / 1024 / 1024}")
      large_files+=$(printf " - $file ($size_mb MB)\n")
    fi
  fi
done < <(gh pr diff "$PR_NUMBER" --name-only)

if [ -n "$large_files" ]; then
  body="## Large file(s) detected

> [!WARNING]
> This PR contains large files!

The following files in this PR exceed the recommended size of ${MAX_SIZE_MB}
MB:

${large_files}

Large files inflate the git repository size permanently, making it slower to
clone for everyone.

Please consider compressing your PDF before committing. You can either use your
existing software for this or an external tool. Here are some guides:

### macOS (Keynote, etc.):

Open the PDF file in Preview, and then click 'File', 'Export', and in 'Quartz
Filter' select 'Reduce File Size'.

### Terminal

With ghostscript installed (e.g. via \`brew install ghostscript\`), run this:

\`\`\`bash
gs \
  -sDEVICE=pdfwrite \
  -dCompatibilityLevel=1.4 \
  -dPDFSETTINGS=/screen \
  -dNOPAUSE \
  -dQUIET \
  -dBATCH \
  -dColorImageResolution=200 \
  -sOutputFile=small.pdf \
  large.pdf
\`\`\`

Make sure to open the PDF file to see if the images still look good. If they're
okay, it would really help if you tried to lower \`200\` to as low as it can go
without compromising visual clarity.

Please re-upload a compressed version and update this PR. You should either
replace the file and run \`git commit --amend\` or \`git squash\` so the large
file is not stored in the git history at all."

  # Post or update the comment
  existing_comment_id=$(gh api "repos/${REPO}/issues/${PR_NUMBER}/comments" \
    --jq '.[] | select(.body | contains("Large file(s) detected")) | .id' \
    | head -n 1)

  if [ -n "$existing_comment_id" ]; then
    gh api "repos/${REPO}/issues/${PR_NUMBER}/comments/${existing_comment_id}" \
      --method PATCH \
      -f body="$body"
    echo "Updated existing comment."
  else
    gh pr comment "$PR_NUMBER" --body "$body"
    echo "Posted new comment."
  fi
else
  echo "All files are within the ${MAX_SIZE_MB} MB limit."
fi
