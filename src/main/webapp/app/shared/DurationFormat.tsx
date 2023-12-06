import dayjs from 'dayjs';
import * as React from 'react';
import { TranslatorContext } from 'react-jhipster';

export interface IDurationFormat {
  value: any;
  blankOnInvalid?: boolean;
  locale?: string;
}

export const DurationFormat = ({ value, blankOnInvalid, locale }: IDurationFormat) => {
  if (blankOnInvalid && !value) {
    return null;
  }

  if (!locale) {
    locale = TranslatorContext.context.locale;
  }

  return (
    <span title={value}>
      {dayjs
        .duration(value)
        .locale(locale || 'fa')
        .humanize()}
    </span>
  );
};
