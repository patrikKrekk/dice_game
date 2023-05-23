package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiceController {
    private static final String JSON_FILE = "rolls.json";

    @FXML
    public ImageView diceImage;

    @FXML
    private Label totalRollsLabel;

    @FXML
    private Label averageLabel;

    @FXML
    public TableView<String> rollsTable = new TableView<>();


    public List<Integer> rolls = new ArrayList<>();

    public void initialize() {
        rolls = loadRollsFromJson();
        initializeRollsTable();
        updateRollsTable();
    }

    public void rollDice() {
        int roll = (int) (Math.random() * 6) + 1;
        rolls.add(roll);
        updateRollsTable();
        updateDiceImage(roll);
        updateTotalRollsLabel();
        updateAverageLabel();
        saveRollsToJson();
    }

    private void updateRollsTable() {
        rollsTable.getItems().clear();
        if (!rolls.isEmpty()) {
            List<String> rollStrings = rolls.stream().map(String::valueOf).collect(Collectors.toList());
            rollStrings.add(0, String.valueOf(rolls.get(rolls.size() - 1)));
            rollsTable.getItems().addAll(rollStrings);
        }
    }

    private void updateDiceImage(int roll) {
        String imageUrl = "/fxml/dice" + roll + ".png";
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        diceImage.setImage(image);
    }

    private void initializeRollsTable() {
        TableColumn<String, String> rollColumn = new TableColumn<>("Roll");
        rollColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        rollsTable.getColumns().add(rollColumn);
    }

    private void updateTotalRollsLabel() {
        totalRollsLabel.setText(String.valueOf(rolls.size()));
    }

    private void updateAverageLabel() {
        double average = rolls.stream().mapToInt(Integer::intValue).average().orElse(0);
        averageLabel.setText(String.format("%.2f", average));
    }

    private List<Integer> loadRollsFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
            JSONArray json = new JSONArray(content);
            List<Integer> rolls = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                rolls.add(json.getInt(i));
            }
            return rolls;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveRollsToJson() {
        JSONArray json = new JSONArray(rolls);
        try (FileWriter fileWriter = new FileWriter(JSON_FILE)) {
            fileWriter.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}