package dlugolecki.pawel.connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlTableCommand {

    private List<String> commands = new ArrayList<>();

    public SqlTableCommand(SqlTableCommandBuilder sqlTableCommandBuilder) {
        this.commands = sqlTableCommandBuilder.commands;
    }

    public static SqlTableCommandBuilder builder() {
        return new SqlTableCommandBuilder();
    }

    public String compact() {
        if (commands.isEmpty()) {
            return "";
        }

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(commands.get(0));
        sqlCommand.append(" ( ");
        sqlCommand.append(commands.stream().skip(1).collect(Collectors.joining(", ")));
        sqlCommand.append(" ); ");
        return sqlCommand.toString();
    }

    public static class SqlTableCommandBuilder {
        private List<String> commands = new ArrayList<>();

        public SqlTableCommandBuilder table(String name) {
            if (commands.isEmpty()) {
                commands.add(MessageFormat.format("create table if not exists {0}", name));
            }
            return this;
        }

        public SqlTableCommandBuilder primaryKey(String name) {
            if (commands.size() == 1) {
                commands.add(MessageFormat.format("{0} integer primary key autoincrement ", name));
            }
            return this;
        }

        public SqlTableCommandBuilder stringColumn(String name, int length, String options) {
            if (commands.size() >= 2) {
                commands.add(MessageFormat.format("{0} varchar({1}) {2} ", name, length, options));
            }
            return this;
        }

        public SqlTableCommandBuilder intColumn(String name, String options) {
            if (commands.size() >= 2) {
                commands.add(MessageFormat.format("{0} integer {1} ", name, options));
            }
            return this;
        }

        public SqlTableCommandBuilder decimalColumn(String name, int scale, int precision, String options) {
            if (commands.size() >= 2) {
                commands.add(MessageFormat.format("{0} decimal({1}, {2}) {3} ", name, scale, precision, options));
            }
            return this;
        }

        public SqlTableCommandBuilder column(String name, String type, String options) {
            if (commands.size() >= 2) {
                commands.add(MessageFormat.format("{0} {1} {2} ", name, type, options));
            }
            return this;
        }

        public SqlTableCommandBuilder foreignKey(String name, String foreignTable, String foreignColumn, String options) {
            if (commands.size() >= 2) {
                commands.add(MessageFormat.format("foreign key ({0}) references {1}({2}) {3} ", name, foreignTable, foreignColumn, options));
            }
            return this;
        }

        public SqlTableCommand build() {
            return new SqlTableCommand(this);
        }
    }
}
