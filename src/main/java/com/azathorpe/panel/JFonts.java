package com.azathorpe.panel;

import javax.swing.*;

public class JFonts {
    public JPanel JPanelX16Font = new JPanel() {{
        this.setFont(Fonts.x16_);
    }};
    public JPanel JPanelX18Font = new JPanel() {{
        this.setFont(Fonts.x18_);
    }};
    public JPanel JPanelX20Font = new JPanel() {{
        this.setFont(Fonts.x20_);
    }};
    public JPanel JPanelX22Font = new JPanel() {{
        this.setFont(Fonts.x22_);
    }};
    public JPanel JPanelX24Font = new JPanel() {{
        this.setFont(Fonts.x24_);
    }};
    public JPanel JPanelX26Font = new JPanel() {{
        this.setFont(Fonts.x26_);
    }};
    public JPanel JPanelX28Font = new JPanel() {{
        this.setFont(Fonts.x28_);
    }};
    public JPanel JPanelX30Font = new JPanel() {{
        this.setFont(Fonts.x30_);
    }};
    public JPanel JPanelX32Font = new JPanel() {{
        this.setFont(Fonts.x32_);
    }};

    public static JLabel JLabelX16Font(){
        return new JLabel() {{
            this.setFont(Fonts.x16_);
        }};
    }
    public static JLabel JLabelX16Font(String title){
        return new JLabel(title) {{
            this.setFont(Fonts.x16_);
        }};
    }
    public static JLabel JLabelX32Font(){
        return new JLabel() {{
            this.setFont(Fonts.x32_);
        }};
    }
    public static JLabel JLabelX32Font(String title){
        return new JLabel(title) {{
            this.setFont(Fonts.x32_);
        }};
    }
}
