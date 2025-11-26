package io.github.qishr.cascara.json.parser;

import java.nio.file.Path;
import java.util.List;

public class Tokenizer {
    public enum TokenType {
    }

    public record Token(TokenType type, String lexeme, int line, int column) {
        @Override
        public String toString() {
            return String.format("[%-18s | '%-15s' | L:%d C:%d]",
                type,
                // Simple escaping for display
                lexeme.replace("\n", "\\n").replace("\r", "\\r").replace("\"", "\\\""),
                line,
                column);
        }
    }

    public static List<Token> tokenize(Path sourcePath, boolean verbose) {
        return null;
    }
}
