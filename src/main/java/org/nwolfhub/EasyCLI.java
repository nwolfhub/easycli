package org.nwolfhub;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EasyCLI {
    private HashMap<String, Template> templates = new HashMap<>();
    private HashMap<String, InputTask> tasks = new HashMap<>();

    public String activeTemplate;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private InputStream in;
    private PrintStream out;

    public String commandNotFoundText = "Command {a} not found";
    public Boolean printNotFoundText = true;

    private void listenOnStream() {
        Scanner sc = new Scanner(in);
        while (true) {
            try {
                String inp = sc.nextLine();
                if (!inp.equals("")) {
                    String command = inp.split(" ")[0];
                    if (!tasks.containsKey(command)) {
                        if (printNotFoundText) {
                            print(commandNotFoundText.replace("{a}", command));
                        }
                    } else {
                        List<String> args = new ArrayList<>();
                        try {
                            args = List.of(inp.substring(command.length() + 1).split(" "));
                        } catch (Exception ignored) {
                        }
                        String[] argArr = args.toArray(new String[0]);
                        tasks.get(command).act(this, argArr);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates an EasyCLI instance
     * @param in - input stream to read info
     * @param out - output stream
     */
    public EasyCLI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        executor.submit(new Thread(this::listenOnStream));
    }

    /**
     * Creates an EasyCLI instance. Uses System streams by standard
     */
    public EasyCLI() {
        this.in = System.in;
        this.out = System.out;
        executor.submit(new Thread(this::listenOnStream));
    }

    public void stopListening() {
        executor.shutdownNow();
    }

    public void startListening() {
        executor.shutdownNow();
        executor = Executors.newFixedThreadPool(1);
        executor.submit(new Thread(this::listenOnStream));
    }

    public void addTemplate(Template template) {
        templates.put(template.getName(), template);
    }
    public void removeTemplate(String name) {
        templates.remove(name);
    }

    public List<Template> getTemplates() {
        return new ArrayList<>(templates.values());
    }

    public void addTask(String command, InputTask task) {
        tasks.put(command, task);
    }

    public void removeTask(String command) {
        tasks.remove(command);
    }

    public List<InputTask> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public String getActiveTemplate() {
        return activeTemplate;
    }

    public EasyCLI setActiveTemplate(String activeTemplate) {
        this.activeTemplate = activeTemplate;
        return this;
    }

    public void print(String text, String template) {
        out.print(templates.get(template).formatText(text));
    }

    public void print(String text) {
        if (templates.size() == 0) {
            templates.put("default", Defaults.defaultTemplate);
        }
        if (activeTemplate == null) {
            activeTemplate = new ArrayList<>(templates.values()).get(templates.size()-1).getName();
        }
        print(text, activeTemplate);
    }
}
