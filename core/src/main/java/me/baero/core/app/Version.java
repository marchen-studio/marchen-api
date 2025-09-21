package me.baero.core.app;

import me.baero.core.exception.InfraErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public record Version(
        int major,
        int minor,
        int patch
) {
    private static final String REGEX = "^\\d+\\.\\d+\\.\\d+$";

    public static Version parse(String version) {
        if (StringUtils.isEmpty(version) || !version.matches(REGEX)) {
            throw InfraErrorCode.PARSING_VERSION_FAILED.toException();
        }

        String[] parts = version.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);

        return new Version(major, minor, patch);
    }

    public boolean isBetween(@Nullable Version fromInclusive, @Nullable Version toExclusive) {
        if (Objects.nonNull(fromInclusive) && this.isLessThan(fromInclusive)) {
            return false;
        }

        return Objects.isNull(toExclusive) || this.isLessThan(toExclusive);
    }

    private boolean isLessThan(Version version) {
        if (this.major < version.major) {
            return true;
        }

        if (this.minor < version.minor) {
            return true;
        }

        return this.patch < version.patch;
    }
}
