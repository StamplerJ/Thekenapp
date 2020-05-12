package com.jann_luellmann.thekenapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.jann_luellmann.thekenapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListTableViewAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_cell_layout, parent, false);

        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @Nullable Cell cellItemModel, int columnPosition, int rowPosition) {
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cellTextview.setText(cellItemModel.getData());

        // Resize
        viewHolder.cellContainer.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cellTextview.requestLayout();
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        return new ColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader columnHeaderItemModel, int columnPosition) {
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.columnHeaderTextView.setText(columnHeaderItemModel.getData());

        // Resize
        columnHeaderViewHolder.columnHeaderContainer.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.columnHeaderTextView.requestLayout();
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel, int rowPosition) {
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.rowHeaderTextView.setText(rowHeaderItemModel.getData());
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    class CellViewHolder extends AbstractViewHolder {

        final LinearLayout cellContainer;
        final TextView cellTextview;

        public CellViewHolder(View itemView) {
            super(itemView);
            cellContainer = itemView.findViewById(R.id.cell_container);
            cellTextview = itemView.findViewById(R.id.cell_data);

        }
    }

    class ColumnHeaderViewHolder extends AbstractViewHolder {

        final LinearLayout columnHeaderContainer;
        final TextView columnHeaderTextView;

        public ColumnHeaderViewHolder(View itemView) {
            super(itemView);
            columnHeaderContainer = itemView.findViewById(R.id.column_header_container);
            columnHeaderTextView = itemView.findViewById(R.id.column_header_textView);
        }
    }

    class RowHeaderViewHolder extends AbstractViewHolder {

        final TextView rowHeaderTextView;

        public RowHeaderViewHolder(View itemView) {
            super(itemView);
            rowHeaderTextView = itemView.findViewById(R.id.row_header_textView);
        }
    }
}
