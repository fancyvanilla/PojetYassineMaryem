    import javax.swing.table.DefaultTableModel;
// Create a new DefaultTableModel with the field names as the column names
    Dao dao=new Dao();
    Map<User,Integer> users=dao.getRanking(userId);
    String[] columnNames = {"Rank", "Name", "Score"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
    // Add rows to the model using the fields of the objects
    Integer i=0;
    for (Map.Entry<User, Integer> entry : users.entrySet()) {
    i++;
    User user = entry.getKey();
    int value = entry.getValue();
    Object[] row = {i, user.getField2(), value};
    model.addRow(row);
    }
    // Set the model for the JTable
    table.setModel(model);
