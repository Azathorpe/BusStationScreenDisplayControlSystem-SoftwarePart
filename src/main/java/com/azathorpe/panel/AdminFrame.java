package com.azathorpe.panel;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.Entities.Bus;
import com.azathorpe.Utils.DataBaseUtils;
import com.azathorpe.Utils.Filter;
import com.azathorpe.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminFrame extends JFrame{
    private JPanel dataPanel = new JPanel(){
        public int row = 0,col = 7;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //画格子出来
            //获取窗口长宽
            int width = this.getWidth();
            int height = this.getHeight();
            //每个格子宽高
            int cellWidth = width / col;
            int cellHeight = height / (app.allBus.size() + 1);
            //画竖线
            for(int i = 0; i <= col; i++) {
                g.drawLine(i * cellWidth, 0, i * cellWidth, height);
            }
            //画横线
            for(int i = 0;i <= app.allBus.size() + 1;i++)
                g.drawLine(0, i * cellHeight, width, i * cellHeight);

            repaint();
        }
    };
    private JScrollPane dataScrollPane = new JScrollPane(dataPanel);

    // 菜单
    private JMenu menu = new JMenu("菜单");
    private JMenuItem refreash = new JMenuItem("刷新");
    private JMenuItem addBusMenuItem = new JMenuItem("添加汽车");
    private JMenuItem removeBusMenuItem = new JMenuItem("删除汽车");
    private JMenuItem updateBusMenuItem = new JMenuItem("更新汽车");
    private JMenuItem queryBusMenuItem = new JMenuItem("查询汽车");
    private JMenuBar menuBar = new JMenuBar();

    public void init(){
        init_Data();
        init_UI();
        init_Event();
    }

    /**
     * 初始化UI界面
     */
    public void init_UI(){
        this.setLayout(new BorderLayout());

        dataPanel.setBackground(Color.GRAY);
        dataPanel.setLayout(new GridLayout(0, 1)); // 每行一个数据项
        this.add(dataScrollPane, BorderLayout.CENTER);

        //添加菜单栏
        this.setJMenuBar(menuBar);
        menuBar.add(menu);
        menuBar.add(refreash);
        menu.add(addBusMenuItem);
        menu.add(removeBusMenuItem);
        menu.add(updateBusMenuItem);
        menu.add(queryBusMenuItem);


        //设置界面大小和位置
        //TODO: x和y设置为居中
        this.setBounds(100, 100, 800, 600);
        //设置显示
        this.setVisible(true);
    }

    /**
     * 初始化事件
     */
    public void init_Event(){
        //添加汽车事件
        addBusMenuItem.addActionListener(e -> {
            PopupAddBus popupAddBus = new PopupAddBus();
            int result = JOptionPane.showConfirmDialog(this, popupAddBus, "添加汽车", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                System.out.println("OK");
            }
        });

        refreash.addActionListener(e -> {
            //每次按下这个菜单按钮 刷新汽车
            refreshData();
        });

        //每15秒钟 自动刷新汽车
        Thread refreshThread = new Thread(() -> {
            while (app.isRunning) {
                refreshData();
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        refreshThread.start();

        // Frame关闭事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                app.isRunning = false;
                dispose();
            }
        });
    }
    /**
     * 初始化数据
     */
    public void init_Data(){
        // 开始的时候更新汽车事件
        queryData();
    }

    /**
     * 刷新data里面的数据
     */
    public void refreshData(){
        // 开始的时候更新汽车事件
        queryData();

        int dataContain = app.allBus.size();
        dataPanel.removeAll();
        dataPanel.setLayout(new GridLayout(dataContain + 1, 7));

        dataPanel.add(JFonts.JLabelX32Font("车次"));
        dataPanel.add(JFonts.JLabelX32Font("车牌"));
        dataPanel.add(JFonts.JLabelX32Font("发车时间"));
        dataPanel.add(JFonts.JLabelX32Font("余票"));
        dataPanel.add(JFonts.JLabelX32Font("终点站"));
        dataPanel.add(JFonts.JLabelX32Font("下一站"));
        dataPanel.add(JFonts.JLabelX32Font("状态"));

        for(Bus bus : app.allBus){
            dataPanel.add(JFonts.JLabelX16Font(bus.getBusID()));
            dataPanel.add(JFonts.JLabelX16Font(bus.getBusName()));
            dataPanel.add(JFonts.JLabelX16Font(bus.getStartTimeInString()));
            dataPanel.add(JFonts.JLabelX16Font(String.valueOf(bus.getTicket())));
            dataPanel.add(JFonts.JLabelX16Font(bus.getDestination()));
            dataPanel.add(JFonts.JLabelX16Font(bus.getNextStation()));
            dataPanel.add(JFonts.JLabelX16Font(bus.getBusStatueInString()));
        }

        //重绘frame
        this.validate();
        this.repaint();
    }

    /**
     * 向数据库请求数据
     */
    private void queryData() {
        String query = DataBaseUtils.getQuery();
        app.allBus.clear();
        ArrayList arrayList = JSON.parseObject(query, ArrayList.class);
        arrayList.forEach(o -> {
            app.allBus.add(JSON.parseObject(JSON.toJSONString(o), Bus.class));
        });

        System.out.println("Updated all bus");
        app.allBus.forEach(System.out::println);
    }

    private void queryData(Filter Filter){
        String query = DataBaseUtils.getQuery();
        app.allBus.clear();
        ArrayList arrayList = JSON.parseObject(query, ArrayList.class);
        arrayList.forEach(o -> {
            app.allBus.add(JSON.parseObject(JSON.toJSONString(o), Bus.class));
        });

        //TODO:通过筛选器筛选出符合要求的内容
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        repaint();
    }

    static{

    }

}
