package util;

import SQLError.DataBaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Error{

    public static boolean isUserIdTaken(Connection connection, int userId) {
        String sql = "SELECT COUNT(*) FROM \"users\" WHERE \"user_id\" = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Unexpected error caused by access to users table.");
        }

        return false;
    }

    public static boolean isCatalogueIdTaken(Connection connection, int catalogueId) {
        String sql = "SELECT COUNT(*) FROM \"catalogues\" WHERE \"catalogue_id\" = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, catalogueId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Unexpected error caused by access to catalogues table.");
        }

        return false;
    }

    public static boolean isMusicIdTaken(Connection connection, int musicId) {
        String sql = "SELECT COUNT(*) FROM \"musics\" WHERE \"music_id\" = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, musicId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Unexpected error caused by access to musics table.");
        }

        return false;
    }

    public static boolean isCreatorIdTaken(Connection connection, int creatorId) {
        String sql = "SELECT COUNT(*) FROM \"creators\" WHERE \"creator_id\" = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, creatorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("SQL error! Unexpected error caused by access to creators table.");
        }

        return false;
    }
}
