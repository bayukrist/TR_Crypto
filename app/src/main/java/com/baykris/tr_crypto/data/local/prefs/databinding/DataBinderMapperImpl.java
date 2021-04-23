package com.baykris.tr_crypto.data.local.prefs.databinding;

import androidx.databinding.MergedDataBinderMapper;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.baykris.tr_crypto.DataBinderMapperImpl());
  }
}
