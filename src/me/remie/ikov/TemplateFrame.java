package me.remie.ikov.template;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.remie.ikov.template.data.TemplateSettings;
import me.remie.ikov.template.types.OffensivePrayer;
import simple.api.ClientContext;
import simple.api.filters.SimpleSkills;
import simple.api.wrappers.SimpleItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public class TemplateFrame extends JFrame {

    private final ClientContext ctx;
    private final TemplateScript script;

    private JComboBox<OffensivePrayer> primaryOffensivePrayerComboBox;

    public TemplateFrame(final TemplateScript script) {
        this.script = script;
        this.ctx = script.ctx;
        initComponents();
    }

    private void initComponents() {
        this.setTitle(script.getName());
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(ctx.mouse.getComponent());

        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane);

        JPanel generalPanel = new JPanel();
        tabbedPane.addTab("General", generalPanel);

        generalPanel.setBorder(new EmptyBorder(5, 5, 0, 5));

        generalPanel.setLayout(new GridLayout(2, 1));

        JLabel foodLabel = new JLabel("Attack spinners: ");
        foodLabel.setToolTipText("If you want to attack spinners");
        generalPanel.add(foodLabel);

        JLabel primaryPrayerLabel = new JLabel("Offensive Prayer: ");
        primaryPrayerLabel.setToolTipText("The offensive prayer you want to use for the portals/spinners in the pest control game.");
        generalPanel.add(primaryPrayerLabel);

        primaryOffensivePrayerComboBox = new JComboBox<>(OffensivePrayer.values());
        generalPanel.add(primaryOffensivePrayerComboBox);

        JPanel infoPanel = new JPanel();
        tabbedPane.addTab("Info", infoPanel);
        infoPanel.setLayout(new BorderLayout());

        JScrollPane infoScrollPane = new JScrollPane();
        infoPanel.add(infoScrollPane, BorderLayout.CENTER);

        JTextPane infoTextArea = new JTextPane();
        infoTextArea.setContentType("text/html");
        infoTextArea.setEditable(false);
        infoTextArea.setPreferredSize(new Dimension(500, 200));
        infoTextArea.setText("Template script for Ikov.");
        infoTextArea.setCaretPosition(0);
        infoScrollPane.setViewportView(infoTextArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        JPanel buttonSubPanel1 = new JPanel();
        buttonSubPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton saveButton = new JButton("Save settings");
        saveButton.addActionListener(e -> saveSettings());
        buttonSubPanel1.add(saveButton);
        JButton loadButton = new JButton("Load settings");
        loadButton.addActionListener(e -> loadSettings());
        buttonSubPanel1.add(loadButton);
        buttonPanel.add(buttonSubPanel1, BorderLayout.NORTH);

        JPanel buttonSubPanel2 = new JPanel();
        buttonSubPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        JButton startScriptButton = new JButton("Start Script");
        startScriptButton.addActionListener(e -> startScript());
        buttonSubPanel2.add(startScriptButton);
        buttonPanel.add(buttonSubPanel2, BorderLayout.SOUTH);

        this.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        this.setVisible(true);
    }

    /**
     * @return
     */
    private List<String> getEquipment() {
        final List<String> equipment = new ArrayList<>();
        for (SimpleItem item : ctx.equipment.populate()) {
            if (item == null) {
                continue;
            }
            equipment.add(item.getName());
        }
        return equipment;
    }

    private void loadCurrentEquipment(DefaultListModel<String> model) {
        final List<String> equipment = getEquipment();
        model.clear();
        for (String item : equipment) {
            model.addElement(item);
        }
    }

    public void startScript() {
        final OffensivePrayer primaryPrayer = (OffensivePrayer) primaryOffensivePrayerComboBox.getSelectedItem();
        if (primaryPrayer == null) {
            JOptionPane.showMessageDialog(this, "You must select a primary offensive prayer.");
            return;
        }
        if (primaryPrayer != OffensivePrayer.NONE) {
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER) < primaryPrayer.getPrayer().getLevel()) {
                JOptionPane.showMessageDialog(this, "You must have a prayer level of " + primaryPrayer.getPrayer().getLevel() + " to use " + primaryPrayer.getName() + ".");
                return;
            }
        }

        final boolean cursesEnabled = primaryPrayer.ordinal() >= 7;
        if (cursesEnabled) {
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER) < 71) {
                JOptionPane.showMessageDialog(this, "You must have a prayer level of 71 to use deflect from melee.");
                return;
            }
        } else {
            if (ctx.skills.getRealLevel(SimpleSkills.Skill.PRAYER) < 43) {
                JOptionPane.showMessageDialog(this, "You must have a prayer level of 43 to use protect from melee.");
                return;
            }
        }

        script.getState().setCursesPrayerEnabled(cursesEnabled);
        script.startScript(getSettings());
    }

    /**
     * Creates a new settings object from the GUI settings.
     *
     * @return the settings object
     */
    private TemplateSettings getSettings() {
        try {
            final OffensivePrayer primaryPrayer = (OffensivePrayer) primaryOffensivePrayerComboBox.getSelectedItem();

            final TemplateSettings settings = new TemplateSettings(primaryPrayer);
            return settings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Opens a file dialog to load settings from a json file
     */
    private void saveSettings() {
        try {
            final TemplateSettings settings = getSettings();

            final File scriptDirectory = script.getStorageDirectory();

            //Open a save file dialog that only allows json files to be selected
            final JFileChooser fileChooser = new JFileChooser(scriptDirectory);
            fileChooser.setDialogTitle("Save settings");
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().endsWith(".json")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".json");
                }
                final Gson gson = new GsonBuilder().setPrettyPrinting().create();
                final String json = gson.toJson(settings);

                final FileWriter writer = new FileWriter(selectedFile);
                writer.write(json);
                writer.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads settings from a json file
     */
    private void loadSettings() {
        try {
            final File scriptDirectory = script.getStorageDirectory();

            final JFileChooser fileChooser = new JFileChooser(scriptDirectory);
            fileChooser.setDialogTitle("Load settings");
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().endsWith(".json")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".json");
                }
                final Gson gson = new GsonBuilder().setPrettyPrinting().create();
                final TemplateSettings settings = gson.fromJson(new FileReader(selectedFile), TemplateSettings.class);


                setupFromSettings(settings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupFromSettings(final TemplateSettings settings) {
        primaryOffensivePrayerComboBox.setSelectedItem(settings.getOffensivePrayer());
    }

    /**
     * Stops the script and disposes the GUI when the window is closed
     */
    @Override
    public void dispose() {
        super.dispose();
        ctx.stopScript();
    }

}
