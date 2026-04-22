const fs = require("fs");
const path = require("path");

const SVG_DIR = "C:\\Users\\fuxiaoshuang\\Desktop\\fontawesome-free-7.0.1-desktop\\svgs\\solid";
const DEFAULT_COLOR = "#3498db"; // 蓝色
const WARNING_COLOR = "#e74c3c"; // 红色

const WARNING_KEYWORDS = ["warning", "alert", "danger", "error", "exclamation", "ban"];

fs.readdirSync(SVG_DIR).forEach((file) => {
  if (file.endsWith(".svg")) {
    const filePath = path.join(SVG_DIR, file);
    let svgContent = fs.readFileSync(filePath, "utf8");

    // 判断是否是警告类图标
    const isWarning = WARNING_KEYWORDS.some(keyword => file.toLowerCase().includes(keyword));
    const targetColor = isWarning ? WARNING_COLOR : DEFAULT_COLOR;

    // 替换 fill 和 stroke 颜色
    svgContent = svgContent.replace(/fill="[^"]*"/g, `fill="${targetColor}"`);
    svgContent = svgContent.replace(/stroke="[^"]*"/g, `stroke="${targetColor}"`);

    fs.writeFileSync(filePath, svgContent);
    console.log(`已修改: ${file} → ${isWarning ? "红色" : "蓝色"}`);
  }
});

console.log("所有 SVG 颜色修改完成！");
