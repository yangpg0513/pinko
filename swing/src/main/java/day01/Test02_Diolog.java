package day01;

import javax.swing.*;
import java.awt.*;

public class Test02_Diolog extends JFrame {                            //创建新类
    public Test02_Diolog() {
        Container container = getContentPane();                 //创建一个容器
        container.setLayout(null);

        JLabel label = new JLabel("这是一个JFrame窗体");      //在窗体中设置标签
        label.setHorizontalAlignment(SwingConstants.CENTER);    //将标签的文字置于标签中间位置
        container.add(label);

        JButton button = new JButton("点我弹出对话框");         //定义一个按钮
        button.setBounds(10, 10, 150, 20);

        //为按钮添加鼠标单击事件
        button.addActionListener(e -> {
            //使MyJDialog窗体可见
            new MyJDialog(Test02_Diolog.this).setVisible(true);
        });

        container.add(button);                             // 将按钮添加到容器中
        container.setBackground(Color.white);

        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //main方法
    public static void main(String args[]) {
        // 实例化MyJDialog类对象
        new Test02_Diolog();
        System.out.println("hello");
    }
}

class MyJDialog extends JDialog {                           //创建新类继承JDialog类

    public MyJDialog(Test02_Diolog frame) {
        //实例化一个JDialog类对象，指定对话框的父窗体、窗体标题和类型
        super(frame, "JDialog窗体", true);

        Container container = getContentPane();                    //创建一个容器
        container.add(new JLabel("这是一个对话框"));           //在容器中添加标签
        setBounds(120, 120, 150, 100);          //设置对话框窗体大小
    }
}