package com.stussy.stussyclone20220930gahyeon.dto.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({ValidationGroups.NotBlankGroup.class,
        ValidationGroups.SizeGroup.class,
        ValidationGroups.PatternCheckGroup.class,
        Default.class
})
public interface ValidationSequence {
}
