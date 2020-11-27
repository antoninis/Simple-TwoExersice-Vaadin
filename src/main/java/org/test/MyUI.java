package org.test;

import javax.servlet.annotation.WebServlet;
import javax.xml.soap.Text;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.io.*;

@Theme("mytheme")
public class MyUI extends UI {
    WhichAreIN whichAreIN = new WhichAreIN();
    ExpandedForm expandedForm = new ExpandedForm();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        VerticalLayout exercise = new VerticalLayout();
        Label nameEx1 = new Label("Задача №1 \n" +
                "Given two arrays of strings a1 and a2 return a sorted array r in lexicographical order of the strings of a1 which are substrings of strings of a2.");
        nameEx1.setWidth("100%");
        Label nameEx2 = new Label("Задача №2 \n" +
                "Write Number in Expanded Form \n" +
                "You will be given a number and you will need to return it as a string in Expanded Form.");
        nameEx2.setWidth("100%");
        Label parameter = new Label("Параметры ввода: Для задачи 1 требуется ввести в поле ввода через Enter две строки в таком формате:" +
                "arp, live, strong.\nДля задачи 2 введите число.");
        parameter.setWidth("100%");
        exercise.addComponents(nameEx1, nameEx2, parameter);

        ComboBox<String> exerciseComBox= new ComboBox<>("Выберите");
        exerciseComBox.setItems("Задача 1", "Задача 2");
        exerciseComBox.setPlaceholder("Выберите номер задачи");
        exerciseComBox.setWidth("100%");
        exerciseComBox.setPlaceholder("Выберите номер задачи");
        exerciseComBox.setEmptySelectionAllowed(false);

        TextArea input = new TextArea("Ввод");
        input.setWidth("100%");
        input.setHeight("80px");

        TextArea output = new TextArea("Вывод");
        output.setWidth("100%");
        output.setHeight("80px");
        output.setReadOnly(true);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button button = new Button("Посчитать");
        button.addClickListener(e -> {
            try {
                if (exerciseComBox.getValue().equals("Задача 1")) {
                    output.setValue(whichAreIN.inArray(input.getValue()));
                }
                if (exerciseComBox.getValue().equals("Задача 2")) {
                    output.setValue(expandedForm.Expanded(input.getValue()));
                }
            }
            catch (NullPointerException e1) {
                output.setValue("Выберите номер задачи");
            }

        });

        Button saveButton = new Button("Сохранить");
        saveButton.addClickListener(e -> {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter( new FileWriter( "file.txt"));
                writer.write(exerciseComBox.getValue()
                        + "\n" + input.getValue() + "\n" + output.getValue());
                new Notification("Сохранено!",
                        Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
            }
            catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
            finally {
                try {
                    if ( writer != null)
                        writer.close( );
                }
                catch ( IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });

        class Uploader implements Upload.Receiver, Upload.SucceededListener {
            public File file;
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                FileOutputStream fos =null;
                try {
                    file = new File(filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE)
                            .show(Page.getCurrent());
                    return null;
                }
                return fos;

            }
            @Override
            public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
                try {
                    BufferedReader reader = new BufferedReader( new FileReader (file));
                    String line = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    while((line = reader.readLine())!= null ) {
                        stringBuilder.append( line );
                        stringBuilder.append( "\n" );
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                    input.setValue(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Uploader receiver = new Uploader();
        Upload upload = new Upload();
        upload.setReceiver(receiver);
        upload.addSucceededListener(receiver);
        upload.setButtonCaption("Загрузка");


        buttonsLayout.addComponents(button,saveButton,upload);

        layout.addComponents(exercise, exerciseComBox, input, output, buttonsLayout);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
