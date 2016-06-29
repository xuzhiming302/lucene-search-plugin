package org.protege.editor.search.ui;

import org.protege.editor.owl.OWLEditorKit;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class NegatedQueryPanel extends QueryPanel {
    private static final long serialVersionUID = 7073335920060606954L;
    private static final String NAME = "Negated Query";
    private QueryEditorPanel editorPanel;

    /**
     * Constructor
     *
     * @param editorKit OWL Editor Kit
     */
    public NegatedQueryPanel(OWLEditorKit editorKit) {
        super(editorKit);
        initUi();
    }

    private void initUi() {
        setLayout(new BorderLayout());
        setBorder(LuceneUiHelper.Utils.EMPTY_BORDER);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new MatteBorder(1, 1, 1, 1, LuceneUiHelper.Utils.MATTE_BORDER_COLOR));
        topPanel.setPreferredSize(new Dimension(0, 36));

        JLabel title = new JLabel("<html><b><i>" + NAME + "</i></b></html>");
        title.setForeground(Color.DARK_GRAY);
        topPanel.add(title, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL, new Insets(3, 5, 0, 5), 0, 0));
        topPanel.add(getCloseButton(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.BASELINE_TRAILING, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        add(topPanel, BorderLayout.NORTH);

        editorPanel = new QueryEditorPanel(editorKit, true, false, false);
        editorPanel.addBasicQuery();
        add(editorPanel, BorderLayout.CENTER);
    }

    private JButton getCloseButton() {
        JButton closeBtn = new JButton(LuceneUiHelper.Utils.getIcon(LuceneUiHelper.Utils.CLOSE_ICON_FILENAME, 11, 11));
        closeBtn.addActionListener(e -> {
            JPanel btnHoldingPanel = (JPanel) closeBtn.getParent().getParent();
            JPanel queriesPanel = (JPanel) btnHoldingPanel.getParent();
            queriesPanel.remove(btnHoldingPanel);
            Container lastPanel = queriesPanel.getParent();
            while(lastPanel != null) {
                lastPanel.revalidate();
                lastPanel.repaint();
                lastPanel = lastPanel.getParent();
            }
        });
        return closeBtn;
    }

    @Override
    boolean isBasicQuery() {
        return false;
    }

    @Override
    boolean isNegatedQuery() {
        return true;
    }

    @Override
    boolean isNestedQuery() {
        return false;
    }

    public QueryEditorPanel getQueryEditorPanel() {
        return editorPanel;
    }
}
