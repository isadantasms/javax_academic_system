#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Gerador de Ícones 3D em Alta Definição (Estilo Fluent/Claymorphism 3D)
Renderiza ícones volumétricos com supersampling 4x, sombras suaves e reflexos especulares.
"""

import math
from PIL import Image, ImageDraw, ImageFilter

def create_canvas(size_final, scale=4):
    w, h = size_final * scale, size_final * scale
    img = Image.new('RGBA', (w, h), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    return img, draw, scale

def downscale(img, size_final):
    return img.resize((size_final, size_final), Image.Resampling.LANCZOS)

def draw_sphere_gradient(draw, bbox, color_center, color_edge, highlight=(0.3, 0.3)):
    x0, y0, x1, y1 = bbox
    cx = x0 + (x1 - x0) * highlight[0]
    cy = y0 + (y1 - y0) * highlight[1]
    radius = max((x1 - x0), (y1 - y0)) / 1.5

    for step in range(40, 0, -1):
        ratio = step / 40.0
        cur_r = radius * ratio
        cur_x0 = cx - cur_r
        cur_y0 = cy - cur_r
        cur_x1 = cx + cur_r
        cur_y1 = cy + cur_r

        # Interpolate color
        r = int(color_center[0] + (color_edge[0] - color_center[0]) * (1.0 - ratio))
        g = int(color_center[1] + (color_edge[1] - color_center[1]) * (1.0 - ratio))
        b = int(color_center[2] + (color_edge[2] - color_center[2]) * (1.0 - ratio))
        a = 255 if len(color_center) < 4 else int(color_center[3] + (color_edge[3] - color_center[3]) * (1.0 - ratio))

        draw.ellipse([cur_x0, cur_y0, cur_x1, cur_y1], fill=(r, g, b, a))

def draw_rounded_rect_3d(img, draw, bbox, r, color_top, color_bot, shadow=True):
    x0, y0, x1, y1 = bbox
    h = y1 - y0

    # Draw soft drop shadow first
    if shadow:
        sh_img = Image.new('RGBA', img.size, (0, 0, 0, 0))
        sh_draw = ImageDraw.Draw(sh_img)
        sh_offset = int(h * 0.08)
        sh_draw.rounded_rectangle([x0 + 4, y0 + sh_offset, x1 - 4, y1 + sh_offset], radius=r, fill=(15, 23, 42, 70))
        sh_img = sh_img.filter(ImageFilter.GaussianBlur(radius=sh_offset * 0.8))
        img.alpha_composite(sh_img)

    # Base gradient
    base_mask = Image.new('L', img.size, 0)
    b_draw = ImageDraw.Draw(base_mask)
    b_draw.rounded_rectangle(bbox, radius=r, fill=255)

    grad = Image.new('RGBA', img.size, (0, 0, 0, 0))
    g_draw = ImageDraw.Draw(grad)
    for y in range(int(y0), int(y1)):
        frac = (y - y0) / max(1, h)
        cr = int(color_top[0] + (color_bot[0] - color_top[0]) * frac)
        cg = int(color_top[1] + (color_bot[1] - color_top[1]) * frac)
        cb = int(color_top[2] + (color_bot[2] - color_top[2]) * frac)
        ca = int(color_top[3] + (color_bot[3] - color_top[3]) * frac) if len(color_top) > 3 else 255
        g_draw.line([(x0, y), (x1, y)], fill=(cr, cg, cb, ca))

    grad.putalpha(base_mask)
    img.alpha_composite(grad)

    # Top highlight line (3D bevel)
    draw.rounded_rectangle([x0 + 2, y0 + 2, x1 - 2, y0 + 4], radius=r // 2, fill=(255, 255, 255, 120))

def make_app_logo_3d(size=64):
    img, draw, s = create_canvas(size)

    # Draw academic graduation cap 3D with shadow and golden tassel
    # Base shadow
    sh = Image.new('RGBA', img.size, (0, 0, 0, 0))
    sh_draw = ImageDraw.Draw(sh)
    sh_draw.ellipse([size*0.15*s, size*0.75*s, size*0.85*s, size*0.95*s], fill=(15, 23, 42, 90))
    sh = sh.filter(ImageFilter.GaussianBlur(radius=6*s))
    img.alpha_composite(sh)

    # Cap base (skull cap cylinder)
    draw_rounded_rect_3d(img, draw, [size*0.28*s, size*0.48*s, size*0.72*s, size*0.78*s], r=12*s,
                         color_top=(37, 99, 235, 255), color_bot=(30, 58, 138, 255), shadow=False)

    # Mortarboard rhombus (3D top perspective)
    pts = [
        (size*0.50*s, size*0.14*s), # Top vertex
        (size*0.88*s, size*0.36*s), # Right vertex
        (size*0.50*s, size*0.56*s), # Bottom vertex
        (size*0.12*s, size*0.36*s), # Left vertex
    ]

    # Draw bottom extrusion (thickness)
    pts_ext = [
        (size*0.12*s, size*0.36*s),
        (size*0.50*s, size*0.56*s),
        (size*0.88*s, size*0.36*s),
        (size*0.88*s, size*0.41*s),
        (size*0.50*s, size*0.61*s),
        (size*0.12*s, size*0.41*s),
    ]
    draw.polygon(pts_ext, fill=(29, 78, 216, 255))

    # Top surface with metallic sheen
    board = Image.new('RGBA', img.size, (0, 0, 0, 0))
    b_draw = ImageDraw.Draw(board)
    b_draw.polygon(pts, fill=(59, 130, 246, 255))

    # Top surface highlight
    b_draw.line([(size*0.15*s, size*0.36*s), (size*0.50*s, size*0.17*s)], fill=(255, 255, 255, 160), width=3*s)
    img.alpha_composite(board)

    # Center button (golden sphere)
    btn_box = [size*0.46*s, size*0.32*s, size*0.54*s, size*0.40*s]
    draw_sphere_gradient(draw, btn_box, (254, 240, 138), (202, 138, 4))

    # Golden Tassel hanging down
    tassel_pts = [
        (size*0.50*s, size*0.36*s),
        (size*0.76*s, size*0.46*s),
        (size*0.80*s, size*0.66*s),
    ]
    draw.line(tassel_pts, fill=(234, 179, 8), width=3*s)
    # Tassel pendant
    draw.ellipse([size*0.77*s, size*0.65*s, size*0.83*s, size*0.75*s], fill=(245, 158, 11))

    return downscale(img, size)

def make_tab_cadastro_3d(size=32):
    img, draw, s = create_canvas(size)

    # ID Card / Clipboard 3D with student profile silhouette and badge
    # Backing board
    draw_rounded_rect_3d(img, draw, [size*0.14*s, size*0.12*s, size*0.86*s, size*0.88*s], r=5*s,
                         color_top=(241, 245, 249, 255), color_bot=(203, 213, 225, 255), shadow=True)

    # Top clip (metallic blue/gold)
    draw_rounded_rect_3d(img, draw, [size*0.35*s, size*0.06*s, size*0.65*s, size*0.22*s], r=3*s,
                         color_top=(96, 165, 250, 255), color_bot=(37, 99, 235, 255), shadow=False)

    # Student avatar circular portrait
    draw_sphere_gradient(draw, [size*0.36*s, size*0.28*s, size*0.64*s, size*0.56*s], (99, 102, 241), (67, 56, 202))

    # Form text lines
    draw.rounded_rectangle([size*0.25*s, size*0.64*s, size*0.75*s, size*0.70*s], radius=2*s, fill=(148, 163, 184, 255))
    draw.rounded_rectangle([size*0.25*s, size*0.75*s, size*0.60*s, size*0.81*s], radius=2*s, fill=(203, 213, 225, 255))

    # Small 3D pencil on corner
    p_pts = [(size*0.70*s, size*0.50*s), (size*0.88*s, size*0.32*s), (size*0.94*s, size*0.38*s), (size*0.76*s, size*0.56*s)]
    draw.polygon(p_pts, fill=(245, 158, 11, 255))
    draw.polygon([(size*0.67*s, size*0.53*s), (size*0.70*s, size*0.50*s), (size*0.76*s, size*0.56*s)], fill=(254, 243, 199, 255))

    return downscale(img, size)

def make_tab_lista_3d(size=32):
    img, draw, s = create_canvas(size)

    # 3D database table / card stack
    # Bottom card
    draw_rounded_rect_3d(img, draw, [size*0.14*s, size*0.28*s, size*0.86*s, size*0.88*s], r=5*s,
                         color_top=(248, 250, 252, 255), color_bot=(226, 232, 240, 255), shadow=True)

    # Table header bar
    draw_rounded_rect_3d(img, draw, [size*0.14*s, size*0.28*s, size*0.86*s, size*0.46*s], r=4*s,
                         color_top=(59, 130, 246, 255), color_bot=(37, 99, 235, 255), shadow=False)

    # Header columns dots
    draw.ellipse([size*0.22*s, size*0.34*s, size*0.28*s, size*0.40*s], fill=(255, 255, 255, 240))
    draw.ellipse([size*0.36*s, size*0.34*s, size*0.42*s, size*0.40*s], fill=(255, 255, 255, 240))
    draw.ellipse([size*0.50*s, size*0.34*s, size*0.56*s, size*0.40*s], fill=(255, 255, 255, 240))

    # Table rows
    draw.rounded_rectangle([size*0.22*s, size*0.54*s, size*0.78*s, size*0.60*s], radius=2*s, fill=(148, 163, 184, 200))
    draw.rounded_rectangle([size*0.22*s, size*0.66*s, size*0.70*s, size*0.72*s], radius=2*s, fill=(203, 213, 225, 220))
    draw.rounded_rectangle([size*0.22*s, size*0.78*s, size*0.74*s, size*0.84*s], radius=2*s, fill=(148, 163, 184, 200))

    # Checkmark badge 3D floating on bottom-right
    badge_box = [size*0.62*s, size*0.62*s, size*0.92*s, size*0.92*s]
    draw_sphere_gradient(draw, badge_box, (52, 211, 153), (5, 150, 105))
    # Checkmark inside
    chk_pts = [(size*0.70*s, size*0.77*s), (size*0.75*s, size*0.82*s), (size*0.84*s, size*0.71*s)]
    draw.line(chk_pts, fill=(255, 255, 255), width=2*s)

    return downscale(img, size)

def make_tab_estatisticas_3d(size=32):
    img, draw, s = create_canvas(size)

    # 3D Bar Chart with isometric volume
    # Base circle shadow
    sh = Image.new('RGBA', img.size, (0, 0, 0, 0))
    sh_draw = ImageDraw.Draw(sh)
    sh_draw.ellipse([size*0.10*s, size*0.74*s, size*0.90*s, size*0.94*s], fill=(15, 23, 42, 80))
    sh = sh.filter(ImageFilter.GaussianBlur(radius=4*s))
    img.alpha_composite(sh)

    # Bar 1 (Left - Teal)
    draw_rounded_rect_3d(img, draw, [size*0.14*s, size*0.48*s, size*0.34*s, size*0.84*s], r=4*s,
                         color_top=(45, 212, 191, 255), color_bot=(13, 148, 136, 255), shadow=False)
    # Bar 2 (Center - Royal Blue, tallest)
    draw_rounded_rect_3d(img, draw, [size*0.40*s, size*0.20*s, size*0.60*s, size*0.84*s], r=4*s,
                         color_top=(96, 165, 250, 255), color_bot=(37, 99, 235, 255), shadow=False)
    # Bar 3 (Right - Indigo)
    draw_rounded_rect_3d(img, draw, [size*0.66*s, size*0.36*s, size*0.86*s, size*0.84*s], r=4*s,
                         color_top=(167, 139, 250, 255), color_bot=(109, 40, 217, 255), shadow=False)

    # Upward glowing trend arrow in gold/amber
    arrow_pts = [
        (size*0.22*s, size*0.42*s),
        (size*0.50*s, size*0.16*s),
        (size*0.80*s, size*0.12*s),
    ]
    draw.line(arrow_pts, fill=(245, 158, 11), width=3*s)
    # Arrowhead
    draw.polygon([(size*0.74*s, size*0.06*s), (size*0.88*s, size*0.12*s), (size*0.82*s, size*0.24*s)], fill=(245, 158, 11))

    return downscale(img, size)

def make_btn_icon_3d(name, size=24):
    img, draw, s = create_canvas(size)

    if name == 'salvar':
        # 3D Floppy/Disc with glowing save check
        draw_rounded_rect_3d(img, draw, [size*0.12*s, size*0.12*s, size*0.88*s, size*0.88*s], r=4*s,
                             color_top=(59, 130, 246, 255), color_bot=(29, 78, 216, 255), shadow=True)
        # Disk metal slider
        draw_rounded_rect_3d(img, draw, [size*0.28*s, size*0.12*s, size*0.72*s, size*0.46*s], r=2*s,
                             color_top=(241, 245, 249, 255), color_bot=(203, 213, 225, 255), shadow=False)
        # Checkmark
        pts = [(size*0.30*s, size*0.66*s), (size*0.45*s, size*0.78*s), (size*0.72*s, size*0.54*s)]
        draw.line(pts, fill=(255, 255, 255), width=2*s)

    elif name == 'limpar':
        # 3D Broom / Eraser with spark
        draw_rounded_rect_3d(img, draw, [size*0.15*s, size*0.15*s, size*0.85*s, size*0.85*s], r=5*s,
                             color_top=(251, 191, 36, 255), color_bot=(217, 119, 6, 255), shadow=True)
        # White circular arrow / refresh inside
        draw.arc([size*0.28*s, size*0.28*s, size*0.72*s, size*0.72*s], start=30, end=300, fill=(255, 255, 255), width=2*s)
        draw.polygon([(size*0.64*s, size*0.22*s), (size*0.76*s, size*0.34*s), (size*0.58*s, size*0.36*s)], fill=(255, 255, 255))

    elif name == 'remover':
        # 3D Red Trash Bin / Delete
        draw_rounded_rect_3d(img, draw, [size*0.15*s, size*0.15*s, size*0.85*s, size*0.85*s], r=5*s,
                             color_top=(248, 113, 113, 255), color_bot=(220, 38, 38, 255), shadow=True)
        # White minus / trash can
        draw.rounded_rectangle([size*0.30*s, size*0.44*s, size*0.70*s, size*0.56*s], radius=2*s, fill=(255, 255, 255))

    elif name == 'atualizar':
        # 3D Cyan/Blue Refresh
        draw_rounded_rect_3d(img, draw, [size*0.15*s, size*0.15*s, size*0.85*s, size*0.85*s], r=5*s,
                             color_top=(56, 189, 248, 255), color_bot=(2, 132, 199, 255), shadow=True)
        draw.arc([size*0.26*s, size*0.26*s, size*0.74*s, size*0.74*s], start=45, end=315, fill=(255, 255, 255), width=2*s)
        draw.polygon([(size*0.66*s, size*0.20*s), (size*0.78*s, size*0.32*s), (size*0.60*s, size*0.36*s)], fill=(255, 255, 255))

    elif name == 'exportar':
        # 3D Emerald Export
        draw_rounded_rect_3d(img, draw, [size*0.15*s, size*0.15*s, size*0.85*s, size*0.85*s], r=5*s,
                             color_top=(52, 211, 153, 255), color_bot=(5, 150, 105, 255), shadow=True)
        # Arrow pointing out
        draw.line([(size*0.50*s, size*0.70*s), (size*0.50*s, size*0.30*s)], fill=(255, 255, 255), width=2*s)
        draw.polygon([(size*0.35*s, size*0.42*s), (size*0.50*s, size*0.24*s), (size*0.65*s, size*0.42*s)], fill=(255, 255, 255))

    elif name == 'calcular':
        # 3D Violet Analytics / Wand
        draw_rounded_rect_3d(img, draw, [size*0.15*s, size*0.15*s, size*0.85*s, size*0.85*s], r=5*s,
                             color_top=(168, 85, 247, 255), color_bot=(126, 34, 206, 255), shadow=True)
        # Lightning / Spark inside
        pts = [(size*0.54*s, size*0.22*s), (size*0.34*s, size*0.50*s), (size*0.52*s, size*0.50*s),
               (size*0.46*s, size*0.78*s), (size*0.68*s, size*0.46*s), (size*0.52*s, size*0.46*s)]
        draw.polygon(pts, fill=(255, 255, 255))

    return downscale(img, size)

def gerar_todos():
    out_dir = "assets/icons"
    icons = [
        ("app_logo_3d.png", make_app_logo_3d(64)),
        ("tab_cadastro_3d.png", make_tab_cadastro_3d(32)),
        ("tab_lista_3d.png", make_tab_lista_3d(32)),
        ("tab_estatisticas_3d.png", make_tab_estatisticas_3d(32)),
        ("btn_salvar_3d.png", make_btn_icon_3d('salvar', 24)),
        ("btn_limpar_3d.png", make_btn_icon_3d('limpar', 24)),
        ("btn_remover_3d.png", make_btn_icon_3d('remover', 24)),
        ("btn_atualizar_3d.png", make_btn_icon_3d('atualizar', 24)),
        ("btn_exportar_3d.png", make_btn_icon_3d('exportar', 24)),
        ("btn_calcular_3d.png", make_btn_icon_3d('calcular', 24)),
    ]

    for fname, img in icons:
        path = f"{out_dir}/{fname}"
        img.save(path, "PNG")
        print(f"Icone salvo: {path}")

if __name__ == "__main__":
    gerar_todos()
