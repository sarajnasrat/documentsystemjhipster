import dayjs from 'dayjs';

export interface IDocumentInfo {
  id?: number;
  number?: string;
  registeredNumber?: string | null;
  issuedate?: string | null;
  subject?: string | null;
  dpriority?: string | null;
  scanPathContentType?: string | null;
  scanPath?: string | null;
  content?: string | null;
  organization?: string | null;
}

export const defaultValue: Readonly<IDocumentInfo> = {};
