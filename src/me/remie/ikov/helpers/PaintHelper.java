package me.remie.ikov.template.helpers;

import me.remie.ikov.template.TemplateScript;
import simple.api.ClientAccessor;
import simple.api.ClientContext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public class PaintHelper extends ClientAccessor<ClientContext> {

    /**
     * This field stores the script instance.
     */
    private final TemplateScript script;

    /**
     * This field stores the name of the script from the manifest.
     * We store it in a field, so we don't have to call the getName method every time we need it.
     */
    private final String scriptName;

    /**
     * This field stores the version of the script from the manifest.
     * We store it in a field, so we don't have to call the getManifest method every time we need it.
     */
    private final String scriptVersion;
    private final int MAX_PAINT_WIDTH = 200;
    private final int MAX_PAINT_HEIGHT = 100;
    private final List<Callable<String>> lines = new ArrayList<>();
    private final Color PAINT_TEXT_COLOR = Color.decode("#e4b734");
    private final Color PAINT_OUTLINE_COLOR = Color.decode("#999999");
    private final Color PAINT_BACKGROUND_COLOR = new Color(0, 0, 0, 220);
    private final Rectangle PAINT_BOUNDS = new Rectangle(5, 2, MAX_PAINT_WIDTH, MAX_PAINT_HEIGHT);
    private int versionTitleXPos = -1;
    private boolean drawingPaint = true;

    /**
     * Constructs a new {@link ClientAccessor} instance.
     *
     * @param script The script instance.
     */
    public PaintHelper(final TemplateScript script) {
        super(script.ctx);
        this.script = script;
        this.scriptName = script.getName();
        this.scriptVersion = script.getManifest().version();

        // Add the default lines to the paint.
        addLine(() -> "Runtime: " + ctx.paint.formatTime(System.currentTimeMillis() - script.getStartTime()));
        addLine(() -> "Status: " + script.getScriptStatus());
    }

    /**
     * This method is called every time the paint is drawn.
     * This method is called by the {@link TemplateScript#onPaint(Graphics2D)} method.
     *
     * @param g the graphics object to draw with
     */
    public void drawPaint(Graphics2D g) {
        if (!drawingPaint) {
            return;
        }
        g.setColor(PAINT_BACKGROUND_COLOR);
        g.fillRoundRect(PAINT_BOUNDS.x, PAINT_BOUNDS.y, PAINT_BOUNDS.width, PAINT_BOUNDS.height, 10, 10);
        g.setColor(PAINT_OUTLINE_COLOR);
        g.drawRoundRect(PAINT_BOUNDS.x, PAINT_BOUNDS.y, PAINT_BOUNDS.width, PAINT_BOUNDS.height, 10, 10);
        g.drawLine(PAINT_BOUNDS.x, 24, PAINT_BOUNDS.x + PAINT_BOUNDS.width, 24);
        g.setColor(PAINT_TEXT_COLOR);
        drawTitleText(g);

        int y = 42;
        for (Callable<String> line : lines) {
            try {
                drawLine(g, line.call(), 12, y);
            } catch (Exception e) {
                e.printStackTrace();
            }
            y += 14;
        }
    }

    /**
     * This method is used to add dynamic lines to the paint.
     *
     * @param line the line to add to the paint
     */
    public void addLine(Callable<String> line) {
        this.lines.add(line);
        reCalculatePaintBounds();
    }

    /**
     * This method recalculates the paint bounds. This is done so that we can dynamically change the size of the paint.
     * This method is called by the {@link PaintHelper#addLine(Callable)} method.
     */
    private void reCalculatePaintBounds() {
        final int height = 30 + (lines.size() * 14);
        PAINT_BOUNDS.setSize(MAX_PAINT_WIDTH, height);
    }

    /**
     * Draws a line of text on the paint.
     *
     * @param g    the graphics object to draw with
     * @param text the text to draw
     * @param x    the x position to draw the text at
     * @param y    the y position to draw the text at
     */
    private void drawLine(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
    }

    /**
     * Draws the title text for the paint. This is done in a separate method so that we can cache the x position of the version text.
     * This is done so that we don't have to calculate the width of the text every time we draw the paint. This is a performance optimization.
     * We accomplish this by checking if the versionTitleXPos is -1. If it is, we calculate the width of the text and store it in the variable.
     *
     * @param g the graphics object to draw with
     */
    private void drawTitleText(Graphics2D g) {
        if (this.versionTitleXPos == -1) {
            final int textWidthRight = g.getFontMetrics().stringWidth(this.scriptVersion);
            this.versionTitleXPos = PAINT_BOUNDS.getBounds().width - textWidthRight - 3;
        }
        drawLine(g, this.scriptName, 12, 20);
        drawLine(g, this.scriptVersion, this.versionTitleXPos, 20);
    }

    /**
     * Handles mouse clicks for the paint. When the user clicks on the paint, it will toggle the paint on and off.
     * This method is called by the {@link TemplateScript#mouseClicked(MouseEvent)} method.
     *
     * @param e the mouse event
     */
    public void handleMouseClick(MouseEvent e) {
        if (PAINT_BOUNDS.contains(e.getPoint())) {
            this.drawingPaint = !this.drawingPaint;
        }
    }

}
