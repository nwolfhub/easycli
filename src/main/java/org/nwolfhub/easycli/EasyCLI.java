package org.nwolfhub.easycli;

import org.nwolfhub.easycli.model.InputTask;
import org.nwolfhub.easycli.model.Template;
import org.nwolfhub.easycli.model.Variable;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
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
    private final VariableProcessor variableProcessor = new VariableProcessor();

    public String commandNotFoundText = "Command {a} not found";
    public Boolean printNotFoundText = true;

    private void listenOnStream() {
        Scanner sc = new Scanner(in);
        int errorCounter = 0;
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
            } catch (NoSuchElementException e) {
                errorCounter++;
            } catch (Exception e) {
                errorCounter++;
                e.printStackTrace();
            }
            if (errorCounter>10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //Il be honest: if this was called user really did hard work to fuck his code up THAT bad
                }
                errorCounter = 0;
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
    }

    /**
     * Creates an EasyCLI instance. Uses System streams
     */
    public EasyCLI() {
        this.in = System.in;
        this.out = System.out;
    }

    /**
     * Creates an EasyCLI instance. Uses System streams
     * @param overrideStreams - will override system streams and disallow printing in them. ErrorStream won't be overridden
     */
    public EasyCLI(boolean overrideStreams) {
        this.in = System.in;
        this.out = System.out;
        if (overrideStreams) {
            System.setIn(new InputStream() {
                @Override
                public int read() {
                    return 0;
                }
            });
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {

                }
            }));
        }
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
    public void addTemplate(Template template, String name) {
        templates.put(name, template);
    }
    public void addTemplate(String name, Template template) {
        templates.put(name, template);
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

    private void setTemplateIfNone() {
        if (templates.size() == 0) {
            templates.put("default", Defaults.defaultTemplate);
        }
        if (activeTemplate == null) {
            activeTemplate = new ArrayList<>(templates.values()).get(templates.size()-1).getName();
        }
    }

    public void print(String text, String template) {
        printInternal(text, templates.get(template));
    }
    public void print(String text, Template template) {
        printInternal(text, template);
    }
    public void print(String text) {
        setTemplateIfNone();
        printInternal(text, templates.get(activeTemplate));
    }

    public void print(Object... objects) {
        setTemplateIfNone();
        printInternal(String.join(" ", Arrays.stream(objects).map(Object::toString).collect(Collectors.joining())), templates.get(activeTemplate));
    }

    public void print(Template template, Object... objects) {
        printInternal(String.join(" ", Arrays.stream(objects).map(Object::toString).collect(Collectors.joining())), template);
    }
    public void print(String template, Object... objects) {
        printInternal(String.join(" ", Arrays.stream(objects).map(Object::toString).collect(Collectors.joining())), templates.get(template));
    }
    public void print(Class<?> template, String text) {
        setTemplateIfNone();
        printInternal(text, templates.values().stream().filter(e -> e.getClass().equals(template)).findFirst().orElse(templates.get(activeTemplate)));
    }
    public void print(String text, Class<?> template) {
        setTemplateIfNone();
        printInternal(text, templates.values().stream().filter(e -> e.getClass().equals(template)).findFirst().orElse(templates.get(activeTemplate)));
    }

    /**
     * Adds a new variable to the list of variables processed by the variable processor.
     *
     * @param v the variable to be added
     */
    public void addVariable(Variable v) {
        variableProcessor.addVariable(v);
    }

    /**
     * Removes the variable at the specified index from the list of variables processed by the variable processor.
     *
     * @param index the index of the variable to be removed
     */
    public void removeVariable(int index) {
        variableProcessor.removeVariable(index);
    }

    /**
     * Clears all the variables in the list of variables processed by the variable processor.
     */
    public void clearVariables() {
        variableProcessor.clearAll();
    }
    /**
     * Returns a list of all variables processed by the variable processor.
     *
     * @return a list of Variable objects
     */
    public List<Variable> getVariables() {
        return variableProcessor.getVariables();
    }


    /**
     * Result method with formatted stuff passed in already
     * @param text
     * @param template
     */
    private void printInternal(String text, Template template) {
        out.print(template.formatText(variableProcessor.processText(text)));
    }
}
